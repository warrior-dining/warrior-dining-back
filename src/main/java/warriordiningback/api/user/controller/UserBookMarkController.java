package warriordiningback.api.user.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/member/bookmarks")
public class UserBookMarkController {

    @GetMapping("/")
    public Map<String, Object> myBookMarkList() {
        return null;
    }

    @PutMapping("/")
    public Map<String, Object> myBookMarkAdd(@RequestBody Map<String, Object> bookmark) {
        return null;
    }
}
