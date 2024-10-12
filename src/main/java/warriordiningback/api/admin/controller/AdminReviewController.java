package warriordiningback.api.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import warriordiningback.api.admin.service.AdminReviewService;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.review.ReviewRepository;

@Slf4j
@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

	@Autowired
	private ReviewRepository reviewsRepository;

	@Autowired
	private AdminReviewService adminReviewService;
	
	@GetMapping("/")
	public Map<String, Object> reviewList(@RequestParam(name = "searchtype", required = false) String searchType,
										  @RequestParam(name = "searchkeyword", required = false) String searchKeyword,
										  @RequestParam(name = "sorttype", required = false) String sortType,
										  Pageable pageable) {
		log.info("sortType:  {}" , sortType);
		return adminReviewService.reviewList(searchType, searchKeyword, sortType ,pageable);
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
	       	review.updateIsdelete(isDeleted); // 상태 직접 업데이트
	        responseMap.put("results", review); // 업데이트된 리뷰 반환
	    } else {
	        responseMap.put("error", "리뷰를 찾을 수 없습니다.");
	    }

	    return responseMap;
	}
}

