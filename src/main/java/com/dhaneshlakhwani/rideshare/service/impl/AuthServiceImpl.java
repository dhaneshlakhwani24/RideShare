package com.dhaneshlakhwani.rideshare.service.impl;

import com.dhaneshlakhwani.rideshare.dto.auth.DriverRegistrationRequest;
import com.dhaneshlakhwani.rideshare.dto.auth.LoginRequest;
import com.dhaneshlakhwani.rideshare.dto.auth.LoginResponse;
import com.dhaneshlakhwani.rideshare.dto.auth.UserRegistrationRequest;
import com.dhaneshlakhwani.rideshare.entity.*;
import com.dhaneshlakhwani.rideshare.repository.*;
import com.dhaneshlakhwani.rideshare.dto.auth.*;
import com.dhaneshlakhwani.rideshare.entity.*;
import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import com.dhaneshlakhwani.rideshare.enums.Role;
import com.dhaneshlakhwani.rideshare.repository.*;
import com.dhaneshlakhwani.rideshare.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    public static final String SESSION_USER_ID = "SESSION_USER_ID";
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverApprovalRequestRepository driverApprovalRequestRepository;

    @Override
    @Transactional
    public void registerUser(UserRegistrationRequest request) {
        validateUniqueMobile(request.getMobileNumber());
        User user = userRepository.save(User.builder()
                .mobileNumber(request.getMobileNumber())
                .password(request.getPassword())
                .role(Role.USER)
                .active(true)
                .build());
        userProfileRepository.save(UserProfile.builder()
                .user(user)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .build());
    }

    @Override
    @Transactional
    public void registerDriver(DriverRegistrationRequest request) {
        validateUniqueMobile(request.getMobileNumber());
        User user = userRepository.save(User.builder()
                .mobileNumber(request.getMobileNumber())
                .password(request.getPassword())
                .role(Role.DRIVER)
                .active(true)
                .build());

        userProfileRepository.save(UserProfile.builder()
                .user(user)
                .fullName(request.getFullName())
                .build());

        DriverProfile driver = driverProfileRepository.save(DriverProfile.builder()
                .user(user)
                .licenseNumber(request.getLicenseNumber())
                .licenseExpiry(request.getLicenseExpiry())
                .aadhaarOrNationalId(request.getAadhaarOrNationalId())
                .approvalStatus(ApprovalStatus.PENDING)
                .build());

        vehicleRepository.save(Vehicle.builder()
                .driver(driver)
                .registrationNumber(request.getRegistrationNumber())
                .vehicleType(request.getVehicleType())
                .brand(request.getBrand())
                .model(request.getModel())
                .color(request.getColor())
                .seatCapacity(request.getSeatCapacity())
                .bookableSeats(Math.max(1, request.getSeatCapacity() - 1))
                .pricePerKm(request.getPricePerKm())
                .online(false)
                .build());

        driverApprovalRequestRepository.save(DriverApprovalRequest.builder()
                .driver(driver)
                .status(ApprovalStatus.PENDING)
                .remarks("Awaiting compliance verification")
                .build());
    }

    @Override
    public LoginResponse login(LoginRequest request, HttpSession session) {
        User user = userRepository.findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid mobile number or password"));
        if (!Objects.equals(user.getPassword(), request.getPassword())) {
            throw new IllegalArgumentException("Invalid mobile number or password");
        }
        session.setAttribute(SESSION_USER_ID, user.getId());
        String fullName = userProfileRepository.findByUser(user).map(UserProfile::getFullName).orElse("User");
        String redirectUrl = switch (user.getRole()) {
            case USER -> "/user/dashboard";
            case DRIVER -> "/driver/dashboard";
            case SUPERADMIN -> "/admin/dashboard";
        };
        return LoginResponse.builder()
                .userId(user.getId())
                .role(user.getRole())
                .redirectUrl(redirectUrl)
                .fullName(fullName)
                .build();
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Override
    public User getCurrentUser(HttpSession session) {
        Object userId = session.getAttribute(SESSION_USER_ID);
        if (userId == null) throw new IllegalStateException("Session expired. Please login again.");
        return userRepository.findById((Long) userId).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    private void validateUniqueMobile(String mobileNumber) {
        userRepository.findByMobileNumber(mobileNumber).ifPresent(v -> {
            throw new IllegalArgumentException("Mobile number already registered");
        });
    }
}
