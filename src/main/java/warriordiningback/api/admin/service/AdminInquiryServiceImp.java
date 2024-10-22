package warriordiningback.api.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.inquiry.InquiriesAnswer;
import warriordiningback.domain.inquiry.InquiriesAnswerRepository;
import warriordiningback.domain.inquiry.Inquiry;
import warriordiningback.domain.inquiry.InquiryRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AdminInquiryServiceImp implements AdminInquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiriesAnswerRepository answerRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Override
    public Map<String, Object> inquiryList(String searchType, String searchKeyword, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Inquiry> searchInquirys;
        resultMap.put("status", false);

        if(searchKeyword != null && !searchKeyword.isEmpty()) {
            switch (searchType) {
                case "title":
                    searchInquirys = inquiryRepository.findAllByTitleContainingOrderByCreatedAtDesc(searchKeyword, pageable);
                    break;
                case "date":
                    LocalDate date = LocalDate.parse(searchKeyword, DateTimeFormatter.ISO_LOCAL_DATE);
                    searchInquirys = inquiryRepository.findAllByCreatedAtContainingOrderByCreatedAtDesc(date, pageable);
                    break;
                case "user":
                    searchInquirys = inquiryRepository.findAllByUserName(searchKeyword, pageable);
                    break;
                case "status":
                    searchInquirys = inquiryRepository.findAllByCodeValue(searchKeyword, pageable);
                    break;
                default:
                    searchInquirys = inquiryRepository.findAllByOrderByCreatedAtDesc(pageable);
                    break;
            }
            resultMap.put("status", true);
        } else {
            searchInquirys =  inquiryRepository.findAllByOrderByCreatedAtDesc(pageable);
            resultMap.put("status", true);
        }
        resultMap.put("results", searchInquirys);
        return resultMap;
    }

    @Override
    public Map<String, Object> inquiryInfo(Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(() -> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));
        resultMap.put("status", true);
        resultMap.put("results", inquiry);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> inquiryAnswerSave(Long id, Map<String, Object> content) {
        Map<String, Object> resultMap = new HashMap<>();
        Inquiry updateInquiryStatus = inquiryRepository.findById(id).orElseThrow(() -> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));

        String email = content.get("email").toString();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
        InquiriesAnswer saveAnswer = InquiriesAnswer.create(content.get("content").toString(), updateInquiryStatus, user);
        saveAnswer = answerRepository.save(saveAnswer);

        Code answerCode = codeRepository.findById(16L).orElseThrow(()-> new DiningApplicationException(ErrorCode.CODE_NOT_FOUND));
        updateInquiryStatus.updateCode(answerCode);

        resultMap.put("status", true);
        resultMap.put("results", updateInquiryStatus);
        return resultMap;
    }
}
