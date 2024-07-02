package com.qtsoftwareltd.invoicing.controllers;

import com.qtsoftwareltd.invoicing.configs.TokenProvider;
import com.qtsoftwareltd.invoicing.dto.ApiResponse;
import com.qtsoftwareltd.invoicing.dto.LoginDto;
import com.qtsoftwareltd.invoicing.dto.LoginResponseDto;
import com.qtsoftwareltd.invoicing.entities.User;
import com.qtsoftwareltd.invoicing.exceptions.InvalidCredentialException;
import com.qtsoftwareltd.invoicing.exceptions.ResourceNotFoundException;
import com.qtsoftwareltd.invoicing.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Login operation")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody @Valid LoginDto loginDto) {
        User user;
        try {
            user = userService.findUserByEmail(loginDto.getEmail());
        } catch (ResourceNotFoundException e) {
            throw new InvalidCredentialException("Invalid credentials");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Invalid credentials");
        }
        return ResponseEntity.ok(new ApiResponse<>(new LoginResponseDto(tokenProvider.generateToken(user))));
    }
}
