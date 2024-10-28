package warriordiningback.api.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.admin.service.AdminUserService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public Map<String, Object> userList(@RequestParam(name = "type", required = false) String searchType,
                                        @RequestParam(name = "keyword", required = false) String searchKeyword,
                                        Pageable pageable) {
        return adminUserService.findByAll(searchType, searchKeyword, pageable);
    }

    @GetMapping("/{userId:[0-9]+}")
    public Map<String, Object> userInfo(@PathVariable("userId") Long userId) {
        return adminUserService.findById(userId);
    }

    @PostMapping("/{userId:[0-9]+}")
    public Map<String, Object> userGrant(@PathVariable("userId") Long userId,
                                         @RequestBody Map<String, Object> role) {
        return adminUserService.handleRole(userId, role);
    }

}
