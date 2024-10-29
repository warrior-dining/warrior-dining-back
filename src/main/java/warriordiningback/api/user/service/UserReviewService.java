package warriordiningback.api.user.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserReviewService {

    Map<String, Object> myReviewList(UserDetails userDetails, Pageable pageable);

    Map<String, Object> myReviewDelete(Long id);

    Map<String, Object> myReviewInfo(Long id);

    Map<String, Object> myReviewInfoEdit(Long id, Map<String, Object> content);

    Map<String, Object> ReservationInfo(Long reservationId);

    Map<String, Object> createReview(Long reservationId, Map<String, Object> content);

}
