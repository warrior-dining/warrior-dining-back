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
import warriordiningback.api.user.oauth.OAuthSuccessHandler;
import warriordiningback.api.user.oauth.service.OAuthService;
import warriordiningback.token.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuthService OAuthService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable) // Bearer 방식을 사용하기 위해 사용하지 않음
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(corsFilter)
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user/signin").permitAll()/*hasRole("('ROLE_USER') or ('ROLE_ADMIN') or ('ROLE_OWNER')")*/
                        .requestMatchers("/api/user/test").permitAll()/*.hasAnyAuthority("USER", "ADMIN", "OWNER")*/
                        .requestMatchers("/api/owner/**").permitAll()/* .hasAnyAuthority("ADMIN", "OWNER") */
                        .requestMatchers("/api/admin/**").permitAll()/* .hasAuthority("ADMIN") */
                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthenticationFilter
                        , UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> {
                    oauth2.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/user/login")); // 프론트에서 요청 할때 사용하는 주소
                    oauth2.redirectionEndpoint(endpoint -> endpoint.baseUri("/api/user/callback/*"));  // 해당 소셜로그인 쪽에서 code, token 발행할때 우리쪽 프로젝트로 호출하는 주소
                    oauth2.userInfoEndpoint(endpoint -> endpoint.userService(OAuthService));
                    oauth2.successHandler(oAuthSuccessHandler);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
