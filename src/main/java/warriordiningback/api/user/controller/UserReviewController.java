package warriordiningback.api.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import warriordiningback.api.user.service.UserReviewService;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.review.ReviewRepository;

@RestController
@RequestMapping("/api/member/reviews")
public class UserReviewController {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserReviewService userReviewService;
	
	@GetMapping("/reservation/{reservationId}")
	public Map<String, Object> ReservationInfo(@PathVariable("reservationId") Long reservationId) {
	    return userReviewService.ReservationInfo(reservationId);
	}
	
	@PostMapping("/reservation/{reservationId}")
	public Map<String, Object> createReview(@PathVariable("reservationId") Long reservationId, @RequestBody Map<String, Object> content) {
		return userReviewService.createReview(reservationId, content);
	}
	
	@GetMapping("/")
	public Map<String, Object> myReviewList(@RequestParam("email") String email, Pageable pageable){
		return userReviewService.myReviewList(email, pageable);
	}
	
	@PatchMapping("/{id:[0-9]+}")
	public Map<String, Object> myReviewDelete(@PathVariable("id") Long id) {
		return userReviewService.myReviewDelete(id);
	}
	
	@GetMapping("/{id:[0-9]+}")
	public Map<String, Object> myReviewInfo(@PathVariable("id") Long id){
		return userReviewService.myReviewInfo(id);
	}
	
	@PutMapping("/{id:[0-9]+}")
	public Map<String, Object> myReviewInfoEdit(@PathVariable("id") Long id, @RequestBody Map<String, Object> content) {
		return userReviewService.myReviewInfoEdit(id, content); 
	}
	
	@DeleteMapping("/{id:[0-9]+}")
	public Map<String, Object> myReviewUpdateStatus(@PathVariable("id") Long id){
		return userReviewService.myReviewDelete(id);
	}
}
