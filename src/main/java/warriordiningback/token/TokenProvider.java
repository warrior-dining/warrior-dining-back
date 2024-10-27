package warriordiningback.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import warriordiningback.api.user.oauth.CustomOAuthUser;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;
import warriordiningback.token.response.TokenResponse;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private final SecretKey key;

    // application.yml에서 secret 값을 가져와 key에 저장
    public TokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // User 정보를 가지고 AccessToken, RefreshToken 생성 로직
    public TokenResponse generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Long flag = null;
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            flag = ((CustomUserDetails) authentication.getPrincipal()).getFlag();
        } else if (authentication.getPrincipal() instanceof CustomOAuthUser) {
            flag = ((CustomOAuthUser) authentication.getPrincipal()).getFlag();
        }

        Map<String, String> header = new HashMap<>();
        header.put("alg", "H256");
        header.put("typ", "JWT");

        // Access Token 생성
        String accessToken = Jwts.builder()
                .header().add(header).and()
                .subject(authentication.getName())
                .issuer("ACCESS")
                .claim("flag", flag)
                .claim("auth", authorities)
                .expiration(getDate(1))
                .issuedAt(Calendar.getInstance().getTime())
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .header().add(header).and()
                .subject(authentication.getName())
                .issuer("REFRESH")
                .claim("flag", flag)
                .claim("auth", authorities)
                .expiration(getDate(1440))
                .issuedAt(Calendar.getInstance().getTime())
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        return TokenResponse.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(true)
                .build();
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 로직
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new DiningApplicationException(ErrorCode.ROLE_INFO_NOT_FOUND);
        }

        if (claims.get("flag") == null) {
            throw new DiningApplicationException(ErrorCode.FLAG_NOT_FOUND);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어 Authentication return
        // UserDetails interface, User: UserDetails를 구현한 class
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // 토큰 정보 검증 로그
    public boolean validateToken(String token) {
        try {
            Claims claims = parseClaims(token);
            log.info("===============================================");
            log.info("|IIssuedTime\t: {}|", claims.getIssuedAt());
            log.info("|ExpireTime\t: {}|", claims.getExpiration());
            log.info("|RealTime\t: {}|", Calendar.getInstance().getTime());
            log.info("===============================================");
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("==============");
            log.error("Token Expired");
        } catch (JwtException exception) {
            log.info("==============");
            log.error("Token Tampered");
        } catch (NullPointerException exception) {
            log.info("==============");
            log.error("Token is null");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    private Date getDate(int interval) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MINUTE, interval);
        return date.getTime();
    }

    public TokenResponse refreshToken(String refreshToken) {
        // 토큰 내용 가져오기
        Authentication authentication = getAuthentication(refreshToken);

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Long flag = null;
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            flag = ((CustomUserDetails) authentication.getPrincipal()).getFlag();
        } else if (authentication.getPrincipal() instanceof CustomOAuthUser) {
            flag = ((CustomOAuthUser) authentication.getPrincipal()).getFlag();
        } else if (authentication.getPrincipal() instanceof User) {
            flag = Long.valueOf(parseClaims(refreshToken).get("flag").toString());
        }

        Map<String, String> header = new HashMap<>();
        header.put("alg", "H256");
        header.put("typ", "JWT");

        // Access Token 생성
        String accessToken = Jwts.builder()
                .header().add(header).and()
                .subject(authentication.getName())
                .issuer("ACCESS")
                .claim("flag", flag)
                .claim("auth", authorities)
                .expiration(getDate(30))
                .issuedAt(Calendar.getInstance().getTime())
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        return TokenResponse.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .status(true)
                .build();
    }

}
