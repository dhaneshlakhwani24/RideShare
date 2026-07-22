package com.dhaneshlakhwani.rideshare.mapper;

import com.dhaneshlakhwani.rideshare.dto.ride.RideRequestDTO;
import com.dhaneshlakhwani.rideshare.entity.RideRequest;

public class RideRequestMapper {

    public static RideRequestDTO toDTO(RideRequest req) {
        if (req == null) return null;

        return RideRequestDTO.builder()
                .id(req.getId())
                .pickupName(req.getPickupName())
                .dropName(req.getDropName())
                .status(req.getStatus().name())
                .requestedSeats(req.getRequestedSeats())
                .estimatedFare(req.getEstimatedFare())
                .build();
    }
}