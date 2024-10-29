package warriordiningback.api.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.service.UserBookMarkService;

import java.util.Map;

@RestController
@RequestMapping("/api/user/bookmarks")
@RequiredArgsConstructor
public class UserBookMarkController {

    private final UserBookMarkService userBookMarkService;

    @GetMapping
    public Map<String, Object> myBookMarkList(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return userBookMarkService.myBookMarkList(userDetails, pageable);
    }

    @PostMapping
    public Map<String, Object> myBookMarkAdd(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Map<String, Object> requestBody) {
        return userBookMarkService.myBookMarkAdd(userDetails, requestBody);
    }

    @DeleteMapping
    public Map<String, Object> myBookMarkDelete(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam("placeId") long placeId) {
        return userBookMarkService.myBookMarkDelete(userDetails, placeId);
    }
}
