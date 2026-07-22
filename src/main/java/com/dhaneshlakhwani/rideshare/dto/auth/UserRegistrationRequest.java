package com.dhaneshlakhwani.rideshare.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @NotBlank private String mobileNumber;
    @NotBlank private String password;
    @NotBlank private String fullName;
    private String email;
}
