package com.dhaneshlakhwani.rideshare.dto.ride;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RideRequestCreateRequest {
    private Long vehicleId;
    private Long driverId;
    private Long rideId;
    private String pickupName;
    private BigDecimal pickupLat;
    private BigDecimal pickupLng;
    private String dropName;
    private BigDecimal dropLat;
    private BigDecimal dropLng;
    private Integer requestedSeats;
    private BigDecimal estimatedFare;
    private String requestMode;
}
