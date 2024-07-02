package com.qtsoftwareltd.invoicing.controllers;

import com.qtsoftwareltd.invoicing.BaseTest;
import com.qtsoftwareltd.invoicing.configs.TokenProvider;
import com.qtsoftwareltd.invoicing.dto.ApiResponse;
import com.qtsoftwareltd.invoicing.dto.LoginDto;
import com.qtsoftwareltd.invoicing.dto.LoginResponseDto;
import com.qtsoftwareltd.invoicing.entities.User;
import com.qtsoftwareltd.invoicing.exceptions.InvalidCredentialException;
import com.qtsoftwareltd.invoicing.exceptions.ResourceNotFoundException;
import com.qtsoftwareltd.invoicing.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class AuthControllerTest extends BaseTest {

    @InjectMocks
    private AuthController authController;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successful login returns token")
    public void successfulLoginReturnsToken() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(tokenProvider.generateToken(any(User.class))).thenReturn("token");

        ResponseEntity<ApiResponse<LoginResponseDto>> response = authController.login(new LoginDto());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token", response.getBody().getPayload().token());
    }

    @Test
    @DisplayName("Invalid email throws InvalidCredentialException")
    public void invalidEmailThrowsInvalidCredentialException() {
        when(userService.findUserByEmail(anyString())).thenThrow(new ResourceNotFoundException("User not found"));

        assertThrows(InvalidCredentialException.class, () -> authController.login(new LoginDto()));
    }

    @Test
    @DisplayName("Invalid password throws InvalidCredentialException")
    public void invalidPasswordThrowsInvalidCredentialException() {
        when(userService.findUserByEmail(anyString())).thenReturn(new User());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidCredentialException.class, () -> authController.login(new LoginDto()));
    }
}