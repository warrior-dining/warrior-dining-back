package warriordiningback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import warriordiningback.api.user.oauth.kakao.OAuth2SuccessHandler;
import warriordiningback.api.user.oauth.kakao.service.KakaoService;
import warriordiningback.token.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@RequiredArgsConstructor
public class SecurityConfig { // 스프링 시큐리티 필터

    private final CorsFilter corsFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final KakaoService kakaoService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // REST API 이므로 basic auth 및 csrf 보안을 사용하지 않음
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable) // Bearer 방식을 사용하기 위해 사용하지 않음
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(corsFilter) // @CrossOrigin는 인증이 없을때 접근, 인증이 있을때는 시큐리티 필터에 등록
//                .addFilter(new LoginFilter((AuthenticationManager) authenticationManagerBuilder))
                // JWT 사용으로 세션 미사용 처리
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user/signin").permitAll()/*hasRole("('ROLE_USER') or ('ROLE_ADMIN') or ('ROLE_OWNER')")*/
                        .requestMatchers("/api/user/test").permitAll()/*.hasAnyAuthority("USER", "ADMIN", "OWNER")*/
                        .requestMatchers("/api/owner/**").permitAll()/* .hasAnyAuthority("ADMIN", "OWNER") */
                        .requestMatchers("/api/admin/**").permitAll()/* .hasAuthority("ADMIN") */
                        .anyRequest().permitAll())
                // Jwt 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(jwtAuthenticationFilter
                        , UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> {
                    oauth2.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/user/login")); // 프론트에서 요청 할때 사용하는 주소
                    oauth2.redirectionEndpoint(endpoint -> endpoint.baseUri("/api/user/callback/*"));  // 해당 소셜로그인 쪽에서 code, token 발행할때 우리쪽 프로젝트로 호출하는 주
                    oauth2.userInfoEndpoint(endpoint -> endpoint.userService(kakaoService));
                    oauth2.successHandler(oAuth2SuccessHandler);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return new BCryptPasswordEncoder();
    }
}
