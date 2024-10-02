package warriordiningback.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warriordiningback.api.user.dto.SignInRequest;
import warriordiningback.api.user.dto.UserRequest;
import warriordiningback.api.user.dto.UserResponse;
import warriordiningback.api.user.service.UserService;
import warriordiningback.domain.user.User;
import warriordiningback.token.response.TokenResponse;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return userService.signIn(signInRequest);
    }

    @PostMapping("/signup")
    public UserResponse signUp(@RequestBody @Valid UserRequest userRequest) {
        User user = userService.signUp(
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getName(),
                userRequest.getBirth(),
                userRequest.getPhone(),
                userRequest.getGender());
        return UserResponse.of(user);
    }

    @PostMapping("/test")
    public String test() {
        return "test 성공";
    }
}
