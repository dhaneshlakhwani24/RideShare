package com.dhaneshlakhwani.rideshare.repository;

import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.entity.Vehicle;
import com.dhaneshlakhwani.rideshare.projection.NearbyVehicleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByDriver(DriverProfile driver);
    long countByOnlineTrue();

    @Query(value = """
        select
            v.id as vehicleId,
            d.id as driverId,
            up.full_name as driverName,
            v.vehicle_type as vehicleType,
            v.registration_number as registrationNumber,
            v.seat_capacity as seatCapacity,
            v.bookable_seats as bookableSeats,
            v.price_per_km as pricePerKm,
            v.current_latitude as latitude,
            v.current_longitude as longitude
        from vehicles v
        join driver_profiles d on v.driver_id = d.id
        join users u on d.user_id = u.id
        join user_profiles up on up.user_id = u.id
        where v.online = true
          and d.approval_status = 'APPROVED'
          and v.current_latitude between :minLat and :maxLat
          and v.current_longitude between :minLng and :maxLng
        """, nativeQuery = true)
    List<NearbyVehicleProjection> findNearbyVehicles(@Param("minLat") BigDecimal minLat,
                                                     @Param("maxLat") BigDecimal maxLat,
                                                     @Param("minLng") BigDecimal minLng,
                                                     @Param("maxLng") BigDecimal maxLng);
}
