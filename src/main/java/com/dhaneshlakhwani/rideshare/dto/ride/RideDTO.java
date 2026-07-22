package com.dhaneshlakhwani.rideshare.dto.ride;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDTO {

    private Long id;
    private String originName;
    private String destinationName;
    private String rideType;
    private String status;
    private LocalDateTime departureTime;
    private BigDecimal baseFare;
    private Integer availableSeats;

    // optional (safe extracted values)
    private String driverName;
    private String vehicleNumber;
}
