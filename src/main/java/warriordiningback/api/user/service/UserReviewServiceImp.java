package warriordiningback.api.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import warriordiningback.api.user.dto.UserReviewResponse;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.reservation.ReservationRepository;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.review.ReviewRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

@Service
@Transactional(readOnly = true)
public class UserReviewServiceImp implements UserReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public Map<String, Object> ReservationInfo(Long reservationId) {
		Map<String, Object> resultMap = new HashMap<>();
	    Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new DiningApplicationException(ErrorCode.RESERVATION_NOT_FOUND));
	    
	    resultMap.put("results", reservation);
	    return resultMap;
	}
	
	@Override
	@Transactional
	public Map<String, Object> createReview(Long reservationId, Map<String, Object> content) {
		Map<String, Object> resultMap = new HashMap<>();
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new DiningApplicationException(ErrorCode.RESERVATION_NOT_FOUND));
		
		Place place = reservation.getPlace();
		User user = reservation.getUser();
		
		int rating = (int)content.get("rating");
		String rivewContent = (String)content.get("review");
		Review newReview = new Review();
	    newReview.create(rating, rivewContent, reservation, place, user);

	    reviewRepository.save(newReview);
	    resultMap.put("results", newReview);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> myReviewList(String email, Pageable pageable) {
		Map<String, Object> resultMap = new HashMap<>();
		Page<Review> reviews;
		User user = userRepository.findByEmail(email).orElseThrow(()-> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
		reviews = reviewRepository.findByUserAndIsDeleted(user.getId(), pageable);
		
		resultMap.put("status", true);
		resultMap.put("results", reviews);
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> myReviewDelete(Long reviewId) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", false);
		Review deleteReview = reviewRepository.findById(reviewId).orElseThrow(()-> new DiningApplicationException(ErrorCode.REVIEW_NOT_FOUND));
		if(deleteReview != null && !deleteReview.isDeleted()) {
			resultMap.put("status", true);
			deleteReview.updateIsdelete(true);
			resultMap.put("results", deleteReview);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> myReviewInfo(Long reviewId) {
		Map<String, Object> resultMap = new HashMap<>();
		Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new DiningApplicationException(ErrorCode.REVIEW_NOT_FOUND));
		UserReviewResponse userReviewResponse = new UserReviewResponse();
		userReviewResponse.setId(review.getId());
		userReviewResponse.setPlaceName(review.getPlace().getName());
		userReviewResponse.setRating(review.getRating());
		userReviewResponse.setContent(review.getContent());
		userReviewResponse.setDeleted(review.isDeleted());
		
		resultMap.put("status", true);
		resultMap.put("results", userReviewResponse);
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> myReviewInfoEdit(Long id, Map<String, Object> content) {
		Map<String, Object> resultMap = new HashMap<>();
		
		Review updateReview = reviewRepository.findById(id).orElseThrow(()-> new DiningApplicationException(ErrorCode.REVIEW_NOT_FOUND));
		updateReview.update(Integer.parseInt(content.get("rating").toString()), content.get("review").toString());
		
		resultMap.put("status", true);
		resultMap.put("results", updateReview);
		return resultMap;
	}

	@Override
	public Map<String, Object> myReviewUpdateStatus(Long reviewId) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", false);
		Review updateReview = reviewRepository.findById(reviewId).orElseThrow(()-> new DiningApplicationException(ErrorCode.REVIEW_NOT_FOUND));
		if(updateReview != null && !updateReview.isDeleted()) {
			resultMap.put("status", true);
			updateReview.updateIsdelete(true);
			resultMap.put("results", updateReview);
		}
		return resultMap;
	}
	
}
