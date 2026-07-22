package com.dhaneshlakhwani.rideshare.controller.api;

import com.dhaneshlakhwani.rideshare.dto.ApiResponse;
import com.dhaneshlakhwani.rideshare.dto.auth.DriverRegistrationRequest;
import com.dhaneshlakhwani.rideshare.dto.auth.LoginRequest;
import com.dhaneshlakhwani.rideshare.dto.auth.LoginResponse;
import com.dhaneshlakhwani.rideshare.dto.auth.UserRegistrationRequest;
import com.dhaneshlakhwani.rideshare.dto.user.UserDTO;
import com.dhaneshlakhwani.rideshare.dto.auth.*;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.mapper.UserMapper;
import com.dhaneshlakhwani.rideshare.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/register-user")
    public ApiResponse<Void> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        authService.registerUser(request);
        return ApiResponse.<Void>builder().success(true).message("User registered successfully").build();
    }

    @PostMapping("/register-driver")
    public ApiResponse<Void> registerDriver(@Valid @RequestBody DriverRegistrationRequest request) {
        authService.registerDriver(request);
        return ApiResponse.<Void>builder().success(true).message("Driver registered and sent for approval").build();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        return ApiResponse.<LoginResponse>builder().success(true).message("Login successful")
                .data(authService.login(request, session)).build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        authService.logout(session);
        return ApiResponse.<Void>builder().success(true).message("Logged out").build();
    }

    @GetMapping("/me")
    public ApiResponse<UserDTO> me(HttpSession session) {

        User user = authService.getCurrentUser(session);

        UserDTO dto = UserMapper.toDTO(user);
        return ApiResponse.<UserDTO>builder().success(true).message("Current session").data(dto).build();
    }
}
