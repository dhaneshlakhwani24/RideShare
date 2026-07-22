package com.dhaneshlakhwani.rideshare.dto.ride;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyVehicleResponse {
    private Long vehicleId;
    private Long driverId;
    private String driverName;
    private String vehicleType;
    private String registrationNumber;
    private Integer seatCapacity;
    private Integer bookableSeats;
    private BigDecimal pricePerKm;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Double distanceKm;
}
