package warriordiningback.api.user.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import warriordiningback.api.user.oauth.CustomOAuthUser;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.user.Role;
import warriordiningback.domain.user.RoleRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final RoleRepository roleRepository;

    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oAuthClientName = request.getClientRegistration().getClientName();

        String name = null;
        String email = null;
        String phone_number = null;
        String birthyear = null;
        String birthday = null;
        String gender = null;
        User user = null;

        if (oAuthClientName.equals("naver")) {
            Map<String, String> naver_account = (Map) oAuth2User.getAttributes().get("response");
            name = naver_account.get("name");
            email = naver_account.get("email");
            phone_number = naver_account.get("mobile_e164");
            birthday = naver_account.get("birthday").replace("-", "");
            birthyear = naver_account.get("birthyear");
            gender = naver_account.get("gender");
        }

        if (oAuthClientName.equals("kakao")) {
            Map kakao_account = (Map) oAuth2User.getAttributes().get("kakao_account");
            if (kakao_account != null) {
                name = kakao_account.get("name").toString();
                email = kakao_account.get("email").toString();
                phone_number = kakao_account.get("phone_number").toString().replace("-", "").replace(" ", "");
                birthday = kakao_account.get("birthday").toString();
                birthyear = kakao_account.get("birthyear").toString();
                gender = kakao_account.get("gender").toString();
            }
        }

        // 신규 회원이면 추가하는 로직!!
        Long newGender = null;
        Code flagCode = null;
        if (email != null) {
            if (userRepository.existsByEmail(email)) {
                user = userRepository.findByEmail(email).orElseThrow(
                        () -> new DiningApplicationException(ErrorCode.USER_NOT_FOUND));
            } else {
                if (gender.equals("male") || gender.equals("M")) {
                    newGender = 3L;
                } else if (gender.equals("female") || gender.equals("F")) {
                    newGender = 4L;
                }

                if (oAuthClientName.equals("kakao")) {
                    flagCode = codeRepository.findById(2L).orElseThrow(() -> new DiningApplicationException(ErrorCode.CODE_NOT_FOUND));
                } else if (oAuthClientName.equals("naver")) {
                    flagCode = codeRepository.findById(18L).orElseThrow(() -> new DiningApplicationException(ErrorCode.CODE_NOT_FOUND));
                }

                Code genderCode = codeRepository.findById(newGender).orElseThrow(
                        () -> new DiningApplicationException(ErrorCode.GENDER_INFO_NOT_FOUND));

                user = User.createKakao(email, name, birthyear + birthday, phone_number, genderCode, flagCode);

                Role defaultRole = roleRepository.findById(1L).orElseThrow(
                        () -> new DiningApplicationException(ErrorCode.ROLE_INFO_NOT_FOUND)
                );
                user.getRoles().add(defaultRole);
                user = userRepository.save(user);
            }
        }

        return CustomOAuthUser.builder()
                .user(user)
                .build();
    }

}
