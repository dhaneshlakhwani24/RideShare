package com.dhaneshlakhwani.rideshare.service;

import com.dhaneshlakhwani.rideshare.dto.auth.DriverRegistrationRequest;
import com.dhaneshlakhwani.rideshare.dto.auth.LoginRequest;
import com.dhaneshlakhwani.rideshare.dto.auth.LoginResponse;
import com.dhaneshlakhwani.rideshare.dto.auth.UserRegistrationRequest;
import com.dhaneshlakhwani.rideshare.entity.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    void registerUser(UserRegistrationRequest request);
    void registerDriver(DriverRegistrationRequest request);
    LoginResponse login(LoginRequest request, HttpSession session);
    void logout(HttpSession session);
    User getCurrentUser(HttpSession session);
}
