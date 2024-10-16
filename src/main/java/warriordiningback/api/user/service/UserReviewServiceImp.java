package warriordiningback.api.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import warriordiningback.domain.review.Review;
import warriordiningback.domain.review.ReviewRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

@Service
public class UserReviewServiceImp implements UserReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Map<String, Object> myReviewList(String email, Pageable pageable) {
		Map<String, Object> resultMap = new HashMap<>();
		Page<Review> reviews;
		User user = userRepository.findByEmail(email).orElseThrow(()-> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
		reviews = reviewRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);
		
		resultMap.put("status", true);
		resultMap.put("results", reviews);
		return resultMap;
	}

	
	
}
