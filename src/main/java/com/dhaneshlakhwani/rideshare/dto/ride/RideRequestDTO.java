package com.dhaneshlakhwani.rideshare.dto.ride;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideRequestDTO {

    private Long id;
    private String pickupName;
    private String dropName;
    private String status;
    private Integer requestedSeats;
    private BigDecimal estimatedFare;
}