package com.dhaneshlakhwani.rideshare.dto.ride;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateRideRequest {
    private Long vehicleId;
    private String originName;
    private BigDecimal originLat;
    private BigDecimal originLng;
    private String destinationName;
    private BigDecimal destinationLat;
    private BigDecimal destinationLng;
    private String departureTime;
    private BigDecimal baseFare;
    private Integer availableSeats;
}
