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
        User existingMember = findUserByEmail(email);
        matchesPassword(user.getPassword(), existingMember.getPassword());
        String encodedPassword = (user.getNewPassword() != null && !user.getNewPassword().isEmpty())
                ? encodedPassword(user.getNewPassword()) : existingMember.getPassword();
        existingMember.edit(user.getPhone(), encodedPassword);
        return existingMember;
    }

    /* ===== 재사용 처리 메서드 ===== */
    // 중복으로 사용되어 해당 서비스내에서 재사용하도록 private으로 생성하였습니다.
    // 다른 Service에서 사용 시 public으로 변경하여 사용하셔도 됩니다.

    // 중복회원 검증
    private void validateUser(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DiningApplicationException(ErrorCode.DUPLICATED_USER_ID);
        }
    }

    // 유저 이메일 조회
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
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

    /* ========== */
}
