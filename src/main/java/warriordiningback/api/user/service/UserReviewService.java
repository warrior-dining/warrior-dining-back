package warriordiningback.api.user.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserReviewService {

	public Map<String, Object> myReviewList(String email, Pageable pageable);
	public Map<String, Object> myReviewDelete(Long id);
	public Map<String, Object> myReviewInfo(Long id);
	public Map<String, Object> myReviewInfoEdit(Long id, Map<String, Object> content);
	public Map<String, Object> myReviewUpdateStatus(Long id);
//	public Map<String, Object> myReviewInfoAdd(Map<String, Object> content);
	
}