package com.dhaneshlakhwani.rideshare.dto.auth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DriverRegistrationRequest {
    @NotBlank private String mobileNumber;
    @NotBlank private String password;
    @NotBlank private String fullName;
    @NotBlank private String licenseNumber;
    private String licenseExpiry;
    private String aadhaarOrNationalId;
    @NotBlank private String registrationNumber;
    @NotBlank private String vehicleType;
    private String brand;
    private String model;
    private String color;
    @Min(2) private Integer seatCapacity;
    private BigDecimal pricePerKm;
}
