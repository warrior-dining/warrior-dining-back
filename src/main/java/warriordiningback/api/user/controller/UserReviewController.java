package warriordiningback.api.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import warriordiningback.api.user.service.UserReviewService;
import warriordiningback.domain.review.ReviewRepository;

@RestController
@RequestMapping("/api/member/reviews")
public class UserReviewController {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserReviewService userReviewService;
	
	@GetMapping("/")
	public Map<String, Object> myReviewList(@RequestParam("email") String email, Pageable pageable){
		return userReviewService.myReviewList(email, pageable);
	}
	
}
