package warriordiningback.api.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.admin.service.AdminReviewService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @GetMapping
    public Map<String, Object> reviewList(@RequestParam(name = "searchtype", required = false) String searchType,
                                          @RequestParam(name = "searchkeyword", required = false) String searchKeyword,
                                          @RequestParam(name = "sorttype", required = false) String sortType,
                                          Pageable pageable) {
        return adminReviewService.reviewList(searchType, searchKeyword, sortType, pageable);
    }

    @PatchMapping("/{id:[0-9]+}")
    public Map<String, Object> updateReviewStatus(@PathVariable("id") Long id) {
        return adminReviewService.updateReviewStatus(id);
    }
}
