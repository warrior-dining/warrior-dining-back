package warriordiningback.api.user.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserInquiryService {

	public Map<String, Object> inquiryList(String email, Pageable pageable);
	public Map<String, Object> inquiryInfo(Long id);
	public Map<String, Object> inquiryInfoEdit(Long id, Map<String, Object> inquiry);
	
}
