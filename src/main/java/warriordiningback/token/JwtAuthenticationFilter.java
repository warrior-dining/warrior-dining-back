package warriordiningback.token;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;
import warriordiningback.exception.dto.DiningExceptionResponse;
import warriordiningback.token.response.TokenResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;
    private final String ACCESS = "Authorization1";
    private final String REFRESH = "Authorization2";

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 1.Request Header에서 Jwt 토큰 추출
        String accessToken = resolveToken((HttpServletRequest) servletRequest, ACCESS);
        String refreshToken = resolveToken((HttpServletRequest) servletRequest, REFRESH);
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try {
            // 2.validateToken으로 토큰 유효성 검사
            if (accessToken != null) {
                if (tokenProvider.validateToken(accessToken)) {
                    // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
                    Authentication authentication = tokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else if (tokenProvider.validateToken(refreshToken)) { // accessToken 만료 되었을 경우
                    // 토큰 다시 생성
                    TokenResponse tokenResponse = tokenProvider.refreshToken(refreshToken);
                    httpResponse.getWriter().append(new Gson().toJson(tokenResponse));
                } else {
                    // 둘다 만료 처리
                    DiningExceptionResponse errorResponse = new DiningExceptionResponse(
                            ErrorCode.TOKEN_NOT_FOUND.getHttpStatus(), false, ErrorCode.TOKEN_NOT_FOUND.getMessage()
                    );
                    httpResponse.setContentType("application/json;charset=UTF-8");
                    httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
                    httpResponse.getWriter().append(new Gson().toJson(errorResponse));
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (DiningApplicationException exception) {
            DiningExceptionResponse errorResponse = new DiningExceptionResponse(
                    exception.getExceptionHttpStatus(), false, exception.getExceptionMessage()
            );

            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.setStatus(exception.getExceptionHttpStatus().value());
            httpResponse.getWriter().append(new Gson().toJson(errorResponse));

        } catch (Exception e) {
            DiningExceptionResponse errorResponse = new DiningExceptionResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR, false, "서버 오류가 발생했습니다." // 에러 메세지 어떻게 띄울지 상의해보기
            );

            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpResponse.getWriter().append(new Gson().toJson(errorResponse));
        }
    }
    
    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest httpServletRequest, String header) {
        String bearerToken = httpServletRequest.getHeader(header);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
