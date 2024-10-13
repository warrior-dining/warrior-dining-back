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
		return adminReviewService.reviewList(searchType, searchKeyword, sortType ,pageable);
	}
	
	
	@PatchMapping("/{id:[0-9]+}")
	public Map<String, Object> updateReviewStatus(@PathVariable("id") Long id) {
	    log.info("호출됨");
	    return adminReviewService.updateReviewStatus(id);
	}
}

