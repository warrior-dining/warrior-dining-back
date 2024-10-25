package warriordiningback.api.user.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import warriordiningback.domain.reservation.Reservation;

public interface UserReviewService {

	public Map<String, Object> myReviewList(UserDetails userDetails, Pageable pageable);
	public Map<String, Object> myReviewDelete(Long id);
	public Map<String, Object> myReviewInfo(Long id);
	public Map<String, Object> myReviewInfoEdit(Long id, Map<String, Object> content);
	public Map<String, Object> myReviewUpdateStatus(Long id);
	public Map<String, Object> ReservationInfo(Long reservationId);
	public Map<String, Object> createReview(Long reservationId, Map<String, Object> content);
	
}
