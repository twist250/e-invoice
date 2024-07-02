package com.qtsoftwareltd.invoicing.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final static String[] swaggerUrls = List.of("/", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/swagger-ui/**").toArray(new String[0]);

    private final static String[] publicUrls = List.of("/api/v1/auth/**", "/api/v1/customers", "/api/v1/invoices").toArray(new String[0]);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        String[] allUrls = Stream.concat(Arrays.stream(swaggerUrls), Arrays.stream(publicUrls)).distinct().toArray(String[]::new);

        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> requests.requestMatchers(allUrls).permitAll().anyRequest().authenticated());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
