package warriordiningback.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warriordiningback.api.user.dto.SignInRequest;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.user.Role;
import warriordiningback.domain.user.RoleRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.token.TokenProvider;
import warriordiningback.token.response.TokenResponse;
import warriordiningback.token.service.CustomUserDetailsService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final CodeRepository codeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public TokenResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호를 확인하세요.");
        }

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

    @Transactional
    public User signUp(String email, String password, String name, String birth, String phone, Long gender) {
        validateUser(email);
        Code code = codeRepository.findById(gender).orElseThrow(
                () -> new RuntimeException("성별에 대한 정보가 존재하지않습니다."));

        String encodedPassword = passwordEncoder.encode(password);
        User savedUser = User.create(email, encodedPassword, name, birth, phone, code);

        Role defaultRole = roleRepository.findById(1L).orElseThrow(
                () -> new RuntimeException("권한에 대한 정보가 존재하지않습니다."));
        savedUser.getRoles().add(defaultRole);

        return userRepository.save(savedUser);
    }

    public void validateUser(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 가입된 이메일이 존재합니다.");
        }
    }

}
