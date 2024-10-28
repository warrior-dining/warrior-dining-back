package warriordiningback.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.service.UserReviewService;

import java.util.Map;

@RestController
@RequestMapping("/api/user/reviews")
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping("/reservation/{reservationId}")
    public Map<String, Object> ReservationInfo(@PathVariable("reservationId") Long reservationId) {
        return userReviewService.ReservationInfo(reservationId);
    }

    @PostMapping("/reservation/{reservationId}")
    public Map<String, Object> createReview(@PathVariable("reservationId") Long reservationId,
                                            @RequestBody Map<String, Object> content) {
        return userReviewService.createReview(reservationId, content);
    }

    @GetMapping
    public Map<String, Object> myReviewList(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return userReviewService.myReviewList(userDetails, pageable);
    }

    @PatchMapping("/{id:[0-9]+}")
    public Map<String, Object> myReviewDelete(@PathVariable("id") Long id) {
        return userReviewService.myReviewDelete(id);
    }

    @GetMapping("/{id:[0-9]+}")
    public Map<String, Object> myReviewInfo(@PathVariable("id") Long id) {
        return userReviewService.myReviewInfo(id);
    }

    @PutMapping("/{id:[0-9]+}")
    public Map<String, Object> myReviewInfoEdit(@PathVariable("id") Long id,
                                                @RequestBody Map<String, Object> content) {
        return userReviewService.myReviewInfoEdit(id, content);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public Map<String, Object> myReviewUpdateStatus(@PathVariable("id") Long id) {
        return userReviewService.myReviewDelete(id);
    }
}
