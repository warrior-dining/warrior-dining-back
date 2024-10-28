package warriordiningback.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.user.dto.UserInquiryResponse;
import warriordiningback.domain.Code;
import warriordiningback.domain.inquiry.*;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserInquiryServiceImp implements UserInquiryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private InquiryQueryRepository inquiryQueryRepository;

    @Autowired
    private InquiriesAnswerRepository answerRepository;

    @Override
    public Map<String, Object> inquiryList(UserDetails userDetails, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Inquiry> inquiry;
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
        inquiry = inquiryQueryRepository.findByUser(user.getId(), pageable);

        resultMap.put("status", true);
        resultMap.put("results", inquiry);
        return resultMap;
    }

    @Override
    public Map<String, Object> inquiryInfo(Long inquiryId) {
        Map<String, Object> resultMap = new HashMap<>();
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(() -> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));
        UserInquiryResponse inquiryResponse = new UserInquiryResponse();
        inquiryResponse.setId(inquiry.getId());
        inquiryResponse.setTitle(inquiry.getTitle());
        inquiryResponse.setContent(inquiry.getContent());
        inquiryResponse.setAnswer(false);
        inquiryResponse.setAnswerContent("");

        if(inquiry.getCode().getId() == 16L){
            InquiriesAnswer answer = answerRepository.findByInquiry(inquiry).orElseThrow(() -> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));
            inquiryResponse.setAnswer(true);
            inquiryResponse.setAnswerContent(answer.getContent());
        }

        resultMap.put("results", inquiryResponse);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> inquiryInfoEdit(Long id, Map<String, Object> inquiry) {
        Map<String, Object> resultMap = new HashMap<>();
        Inquiry updateInquiry = inquiryRepository.findById(id).orElseThrow(() -> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));
        updateInquiry.inquiryUpdate(inquiry.get("title").toString(), inquiry.get("content").toString());

        resultMap.put("status", true);
        resultMap.put("results", updateInquiry);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> newInquiry(Map<String, Object> newInquiries) {
        Map<String, Object> resultMap = new HashMap<>();
        String email = (String) newInquiries.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));

        String title = newInquiries.get("title").toString();
        String content = newInquiries.get("content").toString();

        Code defaultCode = new Code(17L);


        Inquiry inquiry = new Inquiry();
        inquiry.createInquiry(title, content, user, defaultCode);

        inquiryRepository.save(inquiry);
        resultMap.put("results", inquiry);
        return resultMap;
    }

}
