package warriordiningback.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.user.dto.SignInRequest;
import warriordiningback.api.user.dto.UserResponse;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.user.Role;
import warriordiningback.domain.user.RoleRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;
import warriordiningback.token.TokenProvider;
import warriordiningback.token.response.TokenResponse;
import warriordiningback.token.service.CustomUserDetailsService;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final RoleRepository roleRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    // 로그인
    public TokenResponse signIn(SignInRequest signInRequest) {
        User user = findUserByEmail(signInRequest.getEmail());
        boolean userByIsUsed = user.isUsed();
        if (userByIsUsed) {
            matchesPassword(signInRequest.getPassword(), user.getPassword());
            // 1. email + password를 기반으로 Authentication 객체 생성
            // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(signInRequest.getEmail());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, signInRequest.getPassword());

            // 2. 실제 검증: authenticate() 메서드를 통해 요청된 User에 대한 검증 진행
            // authenticate 메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드 실행
            Authentication authentication = authenticationManagerBuilder
                    .getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 Jwt 토큰 생성
            return tokenProvider.generateToken(authentication);
        } else {
            throw new DiningApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }

    // 회원가입
    @Transactional
    public User signUp(String email, String password, String name, String birth, String phone, Long gender) {
        validateUser(email);
        Code genderCode = codeRepository.findById(gender).orElseThrow(
                () -> new DiningApplicationException(ErrorCode.GENDER_INFO_NOT_FOUND));

        Code flagCode = codeRepository.findById(1L).orElseThrow(
                () -> new DiningApplicationException(ErrorCode.CODE_NOT_FOUND));

        String encodedPassword = encodedPassword(password);
        User savedUser = User.create(email, encodedPassword, name, birth, phone, genderCode, flagCode);

        Role defaultRole = roleRepository.findById(1L).orElseThrow(
                () -> new DiningApplicationException(ErrorCode.ROLE_INFO_NOT_FOUND));
        savedUser.getRoles().add(defaultRole);

        return userRepository.save(savedUser);
    }

    // Mypage 내정보
    public UserResponse getCurrentUserInfo(UserDetails userDetails) {
        User user = findUserByEmail(userDetails.getUsername());
        return UserResponse.of(user);
    }

    // 정보 수정
    @Transactional
    public User editUserInfo(String email, User user) {
        User existingUser = findUserByEmail(email);
        matchesPassword(user.getPassword(), existingUser.getPassword());
        String encodedPassword = (user.getNewPassword() != null && !user.getNewPassword().isEmpty())
                ? encodedPassword(user.getNewPassword()) : existingUser.getPassword();
        existingUser.edit(user.getPhone(), encodedPassword);
        return existingUser;
    }

    // 회원 탈퇴
    @Transactional
    public User deleteUserInfo(String email, User user) {
        User existingUser = findUserByEmail(email);
        Long flag = existingUser.getFlag().getId();
        if (flag == 1) {
            matchesPassword(user.getPassword(), existingUser.getPassword());
            existingUser.isEnabled(user.isUsed());
            return existingUser;
        } else if (flag == 2 || flag == 18) {
            existingUser.isEnabled(user.isUsed());
            return existingUser;
        }
        throw new DiningApplicationException(ErrorCode.FLAG_NOT_FOUND);
    }

    @Transactional
    public String findPassword(String name, String birth, String phone) {
        User user = userRepository.findByNameAndBirthAndPhoneAndIsUsedTrue(name, birth, phone);
        if (user == null) {
            throw new DiningApplicationException(ErrorCode.USER_NOT_FOUND);
        } else if (user.getFlag().getId() != 1L) {
            throw new DiningApplicationException(ErrorCode.SOCIAL_LOGIN_NOT_ALLOWED);
        }

        String newPassword = generateRandomPassword();
        user.edit(user.getPhone(), encodedPassword(newPassword));

        return newPassword;
    }

    /* ===== 재사용 처리 메서드 ===== */
    // 중복으로 사용되어 해당 서비스내에서 재사용하도록 private으로 생성하였습니다.
    // 다른 Service에서 사용 시 public으로 변경하여 사용하셔도 됩니다.

    // 중복회원 검증
    private boolean validateUser(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DiningApplicationException(ErrorCode.DUPLICATED_USER_ID);
        }
        return false;
    }

    // 유저 이메일 조회
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndIsUsedTrue(email)
                .orElseThrow(() -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    // 비밀번호 encode
    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // 비밀번호 요청 비밀번호와 DB 비밀번호 매치 확인
    private void matchesPassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new DiningApplicationException(ErrorCode.INVALID_PASSWORD);
        }
    }

    // 랜덤 비밀번호 생성
    private String generateRandomPassword() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@$!%*?&#";
        StringBuilder password = new StringBuilder(12);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 12; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    /* ========== */
}
