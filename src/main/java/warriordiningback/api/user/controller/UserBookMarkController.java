package warriordiningback.api.user.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.service.UserBookMarkService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/member/bookmarks")
public class UserBookMarkController {

    @Autowired
    private UserBookMarkService userBookMarkService;

    @GetMapping("/")
    public Map<String, Object> myBookMarkList(@RequestParam("email") String email, Pageable pageable) {
        return userBookMarkService.myBookMarkList(email, pageable);
    }

    @PutMapping("/")
    public Map<String, Object> myBookMarkAdd(@RequestBody Map<String, Object> requestBody) {
        return userBookMarkService.myBookMarkAdd(requestBody);
    }

    @DeleteMapping("/")
    public Map<String, Object> myBookMarkDelete(@RequestParam("email") String email, @RequestParam("placeId") long placeId) {
        return userBookMarkService.myBookMarkDelete(email, placeId);
    }
}
