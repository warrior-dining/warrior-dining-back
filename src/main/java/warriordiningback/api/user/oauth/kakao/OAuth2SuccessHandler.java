package warriordiningback.api.user.oauth.kakao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import warriordiningback.token.TokenProvider;
import warriordiningback.token.response.TokenResponse;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 위의 정보를 이용하여 토큰 발행 후 아래에서 프론트로 전달
        TokenResponse tokenResponse = tokenProvider.generateToken(authentication);

        //response.sendRedirect("http://localhost:3000/auth/" + tokenResponse.getAccessToken() + );
        String redirectUrl = "http://localhost:3000/SignIn?accessToken=" + tokenResponse.getAccessToken();
        response.sendRedirect(redirectUrl);
    }

}
