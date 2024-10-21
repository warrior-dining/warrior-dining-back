package warriordiningback.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.dto.SignInRequest;
import warriordiningback.api.user.dto.SignUpRequest;
import warriordiningback.api.user.dto.UserResponse;
import warriordiningback.api.user.service.UserService;
import warriordiningback.domain.user.User;
import warriordiningback.token.response.TokenResponse;

@Slf4j
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
    public UserResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        User user = userService.signUp(
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getName(),
                signUpRequest.getBirth(),
                signUpRequest.getPhone(),
                signUpRequest.getGender());
        return UserResponse.of(user);
    }

    @GetMapping
    public UserResponse currentUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getCurrentUserInfo(userDetails);
    }
}
