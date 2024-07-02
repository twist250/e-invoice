package com.qtsoftwareltd.invoicing.configs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "invoicing")
@EnableConfigurationProperties
@Validated
@Data
public class ApplicationConfigs {
    private String jwtSecret;
    private long jwtExpirationInMs;
    private BackDoor backDoor;

    @Data
    public static class BackDoor {
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }
}
