package warriordiningback.api.user.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserInquiryService {

	public Map<String, Object> inquiryList(UserDetails userDetails, Pageable pageable);
	public Map<String, Object> inquiryInfo(Long id);
	public Map<String, Object> inquiryInfoEdit(Long id, Map<String, Object> inquiry);
	public Map<String, Object> newInquiry(Map<String, Object> newInquiries);
	
}
