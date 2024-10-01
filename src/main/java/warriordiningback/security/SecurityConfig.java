package warriordiningback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import warriordiningback.token.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@RequiredArgsConstructor
public class SecurityConfig { // 스프링 시큐리티 필터

    private final CorsFilter corsFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
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
                        .requestMatchers("/api/user/test").hasAnyAuthority("USER", "ADMIN", "OWNER")
                        .requestMatchers("/api/owner/**").hasAnyAuthority("ADMIN", "OWNER")
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll())
                // Jwt 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(jwtAuthenticationFilter
                        , UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
