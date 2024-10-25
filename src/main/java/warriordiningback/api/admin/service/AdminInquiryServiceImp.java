package warriordiningback.api.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.code.CodeService;
import warriordiningback.api.user.service.UserService;
import warriordiningback.domain.inquiry.*;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminInquiryServiceImp implements AdminInquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryQueryRepository inquiryQueryRepository;
    private final InquiriesAnswerRepository answerRepository;
    private final UserService userService;
    private final CodeService codeService;

    @Override
    public Map<String, Object> inquiryList(String searchType, String searchKeyword, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Inquiry> searchInquiries;
        resultMap.put("status", false);

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            searchInquiries = switch (searchType) {
                case "title" -> inquiryRepository.findAllByTitleContainingOrderByCreatedAtDesc(searchKeyword, pageable);
                case "date" -> {
                    LocalDate date = LocalDate.parse(searchKeyword, DateTimeFormatter.ISO_LOCAL_DATE);
                    yield inquiryRepository.findAllByCreatedAtContainingOrderByCreatedAtDesc(date, pageable);
                }
                case "user" -> inquiryQueryRepository.findAllByUserName(searchKeyword, pageable);
                case "status" -> inquiryQueryRepository.findAllByCodeValue(searchKeyword, pageable);
                default -> inquiryRepository.findAllByOrderByCreatedAtDesc(pageable);
            };
            resultMap.put("status", true);
        } else {
            searchInquiries = inquiryRepository.findAllByOrderByCreatedAtDesc(pageable);
            resultMap.put("status", true);
        }
        resultMap.put("results", searchInquiries);
        return resultMap;
    }

    @Override
    public Map<String, Object> inquiryInfo(Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        Inquiry inquiry = findInquiryById(id);
        resultMap.put("status", true);
        resultMap.put("results", inquiry);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> inquiryAnswerSave(Long id, Map<String, Object> content) {
        Map<String, Object> resultMap = new HashMap<>();
        Inquiry updateInquiryStatus = findInquiryById(id);

        String email = content.get("email").toString();
        InquiriesAnswer saveAnswer = InquiriesAnswer.create(
                content.get("content").toString(), updateInquiryStatus, userService.findUserByEmail(email)
        );
        answerRepository.save(saveAnswer);

        updateInquiryStatus.updateCode(codeService.findCodeById(16L));
        resultMap.put("status", true);
        resultMap.put("results", updateInquiryStatus);
        return resultMap;
    }

    private Inquiry findInquiryById(Long id) {
        return inquiryRepository.findById(id).orElseThrow(()
                -> new DiningApplicationException(ErrorCode.INQUIRY_INFO_NOT_FOUND));
    }
}
