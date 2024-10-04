package warriordiningback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정
        config.setAllowedOriginPatterns(List.of("*"));
//        config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용
        config.addAllowedMethod("*"); // 모든 POST, GET, PUT, PATCH 요청을 허용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
