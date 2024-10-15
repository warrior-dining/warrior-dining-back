package warriordiningback.api.user.oauth.kakao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import warriordiningback.api.user.oauth.kakao.CustomOAuth2User;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.domain.user.Role;
import warriordiningback.domain.user.RoleRepository;
import warriordiningback.domain.user.User;
import warriordiningback.domain.user.UserRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final RoleRepository roleRepository;

    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oAuthClientName = request.getClientRegistration().getClientName();

        try {
            log.info("{} : {}", oAuthClientName, new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id = null;
        String name = null;
        String email = null;
        String phone_number = null;
        String birthyear = null;
        String birthday = null;
        String gender = null;

        if (oAuthClientName.equals("naver")) {
            Map<String, String> responseMap = (Map) oAuth2User.getAttributes().get("response");
            id = responseMap.get("id");
            email = responseMap.get("email");
            name = responseMap.get("nickname");
        }

        if (oAuthClientName.equals("kakao")) {
            id = oAuth2User.getAttributes().get("id").toString();
            Map kakao_account = (Map) oAuth2User.getAttributes().get("kakao_account");
            if (kakao_account != null) {
                log.info("kakao_account : {}", kakao_account);
                name = kakao_account.get("name").toString();
                email = kakao_account.get("email").toString();
                phone_number = kakao_account.get("phone_number").toString();
                birthday = kakao_account.get("birthday").toString();
                birthyear = kakao_account.get("birthyear").toString();
                gender = kakao_account.get("gender").toString();
                log.info("name : {}, phone_number : {}, birthday : {}, gender : {}, password : {}", name, phone_number, birthday, gender);

            }
        }

        // 신규 회원이면 추가하는 로직!!
        Long genderCode = null;
        if (email != null && userRepository.findByEmail(email).isEmpty()) {
            if (gender.equals("male")) {
                genderCode = 3L;
            } else if (gender.equals("female")) {
                genderCode = 4L;
            }
            Code code = codeRepository.findById(genderCode).orElseThrow(
                    () -> new DiningApplicationException(ErrorCode.GENDER_INFO_NOT_FOUND));

            User savedUser = User.createKakao(email, name, birthyear + birthday, phone_number, code);

            Role defaultRole = roleRepository.findById(1L).orElseThrow(
                    () -> new DiningApplicationException(ErrorCode.ROLE_INFO_NOT_FOUND)
            );
            savedUser.getRoles().add(defaultRole);
            userRepository.save(savedUser);
        }


        return CustomOAuth2User.builder()
                .name(name)
                .email(email)
                .phone(phone_number)
                .birth(birthday)
                .gender(gender)
                .build();
    }

}
