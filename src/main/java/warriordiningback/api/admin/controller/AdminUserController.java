package warriordiningback.api.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import warriordiningback.api.admin.service.AdminUserService;
import warriordiningback.domain.user.RoleRepository;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin/members")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public Map<String, Object> userList (){
        return adminUserService.findByAll();
    }

    @GetMapping("/info/{userId:[0-9]+}")
    public Map<String, Object> userInfo(@PathVariable("userId") Long userId){
        return adminUserService.findById(userId);
    }

    @PostMapping("/info/{userId:[0-9]+}")
    public Map<String, Object> userGrant(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> role){
        return adminUserService.handleRole(userId, role);
    }

}
