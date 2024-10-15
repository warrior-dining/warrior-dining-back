package warriordiningback.api.user.oauth;

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
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        TokenResponse tokenResponse = tokenProvider.generateToken(authentication);

        String redirectUrl = "http://localhost:3000/SignIn?accessToken=" + tokenResponse.getAccessToken();
        response.sendRedirect(redirectUrl);
    }

}
