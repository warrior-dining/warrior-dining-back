package warriordiningback.api.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.review.ReviewRepository;

@Slf4j
@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

	@Autowired
	private ReviewRepository reviewsRepository;
	
	@GetMapping("/")
	public Map<String, Object> review() {
		Map<String, Object> map = new HashMap<>();
		map.put("data", reviewsRepository.findAll());
		return map;
	}
	
	
	@PatchMapping("/{id:[0-9]+}")
	public Map<String, Object> updateReviewStatus(@PathVariable("id") Long id,
	                                              @RequestBody Map<String, Long> statusMap) {
	    log.info("호출됨");

	    // Long을 Boolean으로 변환 (1이면 true, 그 외에는 false)
	    Boolean isDeleted = statusMap.get("status") != null && statusMap.get("status") == 1;
	    Map<String, Object> responseMap = new HashMap<>();
	    log.info("isDeleted : {} ",isDeleted);

	    if (reviewsRepository.existsById(id)) {
	    	Review review = reviewsRepository.findById(id).orElseThrow(()-> new RuntimeException("해당아이디 없다"));
	        Review updateReview = Review.updateIsdelete(id, review.getRating(), review.getContent(), isDeleted, review.getReservation(), review.getPlace(), review.getUser()); // 상태 직접 업데이트
	        updateReview = reviewsRepository.save(updateReview);
	        responseMap.put("results", updateReview); // 업데이트된 리뷰 반환
	    } else {
	        responseMap.put("error", "리뷰를 찾을 수 없습니다.");
	    }

	    return responseMap;
	}
}

