package warriordiningback.api.admin.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminInquiryService {

    Map<String, Object> inquiryList(String searchType, String searchKeyword, Pageable pageable);

    Map<String, Object> inquiryInfo(Long id);

    Map<String, Object> inquiryAnswerSave(Long id, Map<String, Object> content);
}
