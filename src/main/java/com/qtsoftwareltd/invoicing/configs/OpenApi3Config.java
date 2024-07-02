package com.qtsoftwareltd.invoicing.configs;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
        )
})
public class OpenApi3Config {
    @Bean
    public OpenAPI mistOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Invoice System API")
                        .version("V1")
                        .description("Invoice System API")
                        .termsOfService("https://swagger.io/terms/")
                        .license(
                                new License().name("Apache 2.0")
                                        .url("https://springdoc.org")
                        )
        );
    }
}