package com.dhaneshlakhwani.rideshare.repository;

import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.entity.Ride;
import com.dhaneshlakhwani.rideshare.enums.RideStatus;
import com.dhaneshlakhwani.rideshare.enums.RideType;
import com.dhaneshlakhwani.rideshare.projection.SharedRideProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByDriverOrderByDepartureTimeDesc(DriverProfile driver);
    long countByDriverAndRideTypeAndStatus(DriverProfile driver, RideType rideType, RideStatus status);

    @Query(value = """
        select
            r.id as rideId,
            d.id as driverId,
            v.id as vehicleId,
            up.full_name as driverName,
            r.origin_name as originName,
            r.destination_name as destinationName,
            r.base_fare as baseFare,
            r.available_seats as availableSeats,
            r.departure_time as departureTime
        from rides r
        join driver_profiles d on r.driver_id = d.id
        join vehicles v on r.vehicle_id = v.id
        join users u on d.user_id = u.id
        join user_profiles up on up.user_id = u.id
        where r.ride_type = 'SHARED'
          and r.status in ('SCHEDULED', 'ACTIVE')
          and r.available_seats > 0
          and r.origin_lat between :originMinLat and :originMaxLat
          and r.origin_lng between :originMinLng and :originMaxLng
          and r.destination_lat between :destMinLat and :destMaxLat
          and r.destination_lng between :destMinLng and :destMaxLng
        order by r.departure_time asc
        """, nativeQuery = true)
    List<SharedRideProjection> searchSharedRides(@Param("originMinLat") BigDecimal originMinLat,
                                                 @Param("originMaxLat") BigDecimal originMaxLat,
                                                 @Param("originMinLng") BigDecimal originMinLng,
                                                 @Param("originMaxLng") BigDecimal originMaxLng,
                                                 @Param("destMinLat") BigDecimal destMinLat,
                                                 @Param("destMaxLat") BigDecimal destMaxLat,
                                                 @Param("destMinLng") BigDecimal destMinLng,
                                                 @Param("destMaxLng") BigDecimal destMaxLng);
}
