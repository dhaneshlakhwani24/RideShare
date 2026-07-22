package com.dhaneshlakhwani.rideshare.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SharedRideProjection {
    Long getRideId();
    Long getDriverId();
    Long getVehicleId();
    String getDriverName();
    String getOriginName();
    String getDestinationName();
    BigDecimal getBaseFare();
    Integer getAvailableSeats();
    LocalDateTime getDepartureTime();
}
