package warriordiningback.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    // Swagger UI 화면 표시 설정
    private Info apiInfo() {
        return new Info()
                .title("warrior dining back")
                .description("REST API 관한 유저 및 인증 등 요청 테스트")
                .version("1.0.0");
    }

    private final String ACCESS = "Authorization_Access";
    private final String REFRESH = "Authorization_Refresh";

    // Swagger 빈 등록
    @Bean
    public OpenAPI openAPI() {
        // token 정의
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(ACCESS)
                .addList(REFRESH);

        // Security Scheme 정의
        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(ACCESS);

        SecurityScheme refreshTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(REFRESH);

        Components components = new Components()
                .addSecuritySchemes(ACCESS, accessTokenSecurityScheme)
                .addSecuritySchemes(REFRESH, refreshTokenSecurityScheme);

        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}
