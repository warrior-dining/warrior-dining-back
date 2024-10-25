package warriordiningback.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import warriordiningback.api.user.dto.*;
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

    @PutMapping
    public UserResponse editUserInfo(@RequestBody UserEditRequest userEditRequest,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.editUserInfo(userDetails.getUsername(), userEditRequest.toEntity());
        return UserResponse.of(user);
    }

    @DeleteMapping
    public UserResponse deleteUserInfo(@Valid @RequestBody UserDeletedRequest userDeletedRequest,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.deleteUserInfo(userDetails.getUsername(), userDeletedRequest.toEntity());
        return UserResponse.of(user);
    }

    @PostMapping
    public FindPasswordResponse findPassword(@RequestBody @Valid FindPasswordRequest findPasswordRequest) {
        String newPassword = userService.findPassword(
                findPasswordRequest.getName(),
                findPasswordRequest.getBirth(),
                findPasswordRequest.getPhone()
        );
        return FindPasswordResponse.of(newPassword);
    }
}
