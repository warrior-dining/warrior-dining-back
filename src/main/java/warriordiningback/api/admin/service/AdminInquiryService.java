package warriordiningback.api.admin.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AdminInquiryService {

    public Map<String, Object> inquiryList(String searchType, String searchKeyword, Pageable pageable);
    public Map<String, Object> inquiryInfo(Long id);
    public Map<String, Object> inquiryAnswerSave(Long id, Map<String, String> content);
}
