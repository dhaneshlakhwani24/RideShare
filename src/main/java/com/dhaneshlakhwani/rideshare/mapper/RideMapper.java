package com.dhaneshlakhwani.rideshare.mapper;

import com.dhaneshlakhwani.rideshare.dto.ride.RideDTO;
import com.dhaneshlakhwani.rideshare.entity.Ride;

public class RideMapper {

    public static RideDTO toDTO(Ride ride) {
        if (ride == null) return null;

        return RideDTO.builder()
                .id(ride.getId())
                .originName(ride.getOriginName())
                .destinationName(ride.getDestinationName())
                .rideType(ride.getRideType().name())
                .status(ride.getStatus().name())
                .departureTime(ride.getDepartureTime())
                .baseFare(ride.getBaseFare())
                .availableSeats(ride.getAvailableSeats())

                // SAFE extraction from relations
                .driverName(
                        ride.getDriver() != null
                                ? ride.getDriver().getUser().getMobileNumber()
                                : null
                )
                .vehicleNumber(
                        ride.getVehicle() != null
                                ? ride.getVehicle().getRegistrationNumber()
                                : null
                )
                .build();
    }
}
