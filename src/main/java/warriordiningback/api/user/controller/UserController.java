package warriordiningback.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.dto.user.UserRequest;
import warriordiningback.api.user.service.UserService;
import warriordiningback.token.response.TokenResponse;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody UserRequest userRequest) {
        return userService.signIn(userRequest);
    }

    @PostMapping("/test")
    public String test() {
        return "test 성공";
    }
}
