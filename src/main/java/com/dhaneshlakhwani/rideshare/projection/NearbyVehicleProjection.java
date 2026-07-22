package com.dhaneshlakhwani.rideshare.projection;

import java.math.BigDecimal;

public interface NearbyVehicleProjection {
    Long getVehicleId();
    Long getDriverId();
    String getDriverName();
    String getVehicleType();
    String getRegistrationNumber();
    Integer getSeatCapacity();
    Integer getBookableSeats();
    BigDecimal getPricePerKm();
    BigDecimal getLatitude();
    BigDecimal getLongitude();
}
