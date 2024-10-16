package warriordiningback.api.user.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserReviewService {

	public Map<String, Object> myReviewList(@RequestParam("email") String email, Pageable pageable);
	
}
