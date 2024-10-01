package warriordiningback.token.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
