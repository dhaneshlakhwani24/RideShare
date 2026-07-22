package com.dhaneshlakhwani.rideshare.service.impl;

import com.dhaneshlakhwani.rideshare.dto.dashboard.UserDashboardResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.NearbyVehicleResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.RideRequestCreateRequest;
import com.dhaneshlakhwani.rideshare.entity.*;
import com.dhaneshlakhwani.rideshare.repository.DriverProfileRepository;
import com.dhaneshlakhwani.rideshare.repository.RideRepository;
import com.dhaneshlakhwani.rideshare.repository.RideRequestRepository;
import com.dhaneshlakhwani.rideshare.repository.VehicleRepository;
import com.dhaneshlakhwani.rideshare.entity.*;
import com.dhaneshlakhwani.rideshare.enums.RequestStatus;
import com.dhaneshlakhwani.rideshare.enums.RideType;
import com.dhaneshlakhwani.rideshare.projection.NearbyVehicleProjection;
import com.dhaneshlakhwani.rideshare.projection.SharedRideProjection;
import com.dhaneshlakhwani.rideshare.repository.*;
import com.dhaneshlakhwani.rideshare.service.NotificationService;
import com.dhaneshlakhwani.rideshare.service.UserService;
import com.dhaneshlakhwani.rideshare.util.GeoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final VehicleRepository vehicleRepository;
    private final RideRepository rideRepository;
    private final RideRequestRepository rideRequestRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final NotificationService notificationService;

    @Override
    @Async
    public CompletableFuture<List<NearbyVehicleResponse>> findNearbyVehicles(BigDecimal lat, BigDecimal lng, BigDecimal radiusKm) {
        BigDecimal latDelta = GeoUtil.latitudeDelta(radiusKm);
        BigDecimal lngDelta = GeoUtil.longitudeDelta(lat, radiusKm);
        List<NearbyVehicleResponse> result = vehicleRepository.findNearbyVehicles(
                        lat.subtract(latDelta), lat.add(latDelta),
                        lng.subtract(lngDelta), lng.add(lngDelta))
                .stream()
                .map(row -> mapNearbyVehicle(row, lat, lng))
                .filter(v -> v.getDistanceKm() <= radiusKm.doubleValue())
                .sorted(Comparator.comparing(NearbyVehicleResponse::getDistanceKm))
                .toList();
        return CompletableFuture.completedFuture(result);
    }

    @Override
    @Async
    public CompletableFuture<List<SharedRideProjection>> searchSharedRides(BigDecimal originLat, BigDecimal originLng,
                                                                           BigDecimal destinationLat, BigDecimal destinationLng,
                                                                           BigDecimal radiusKm) {
        BigDecimal oLat = GeoUtil.latitudeDelta(radiusKm);
        BigDecimal oLng = GeoUtil.longitudeDelta(originLat, radiusKm);
        BigDecimal dLat = GeoUtil.latitudeDelta(radiusKm);
        BigDecimal dLng = GeoUtil.longitudeDelta(destinationLat, radiusKm);
        return CompletableFuture.completedFuture(rideRepository.searchSharedRides(
                originLat.subtract(oLat), originLat.add(oLat),
                originLng.subtract(oLng), originLng.add(oLng),
                destinationLat.subtract(dLat), destinationLat.add(dLat),
                destinationLng.subtract(dLng), destinationLng.add(dLng)
        ));
    }

    @Override
    @Transactional
    public RideRequest createRideRequest(User user, RideRequestCreateRequest request) {
        RideType rideType = "SHARED".equalsIgnoreCase(request.getRequestMode()) ? RideType.SHARED : RideType.STANDARD;
        DriverProfile driver = request.getDriverId() != null ? driverProfileRepository.findById(request.getDriverId()).orElse(null) : null;
        Vehicle vehicle = request.getVehicleId() != null ? vehicleRepository.findById(request.getVehicleId()).orElse(null) : null;
        Ride ride = request.getRideId() != null ? rideRepository.findById(request.getRideId()).orElse(null) : null;
        if (ride != null && driver == null) driver = ride.getDriver();
        if (ride != null && vehicle == null) vehicle = ride.getVehicle();

        RideRequest rideRequest = rideRequestRepository.save(RideRequest.builder()
                .user(user)
                .driver(driver)
                .vehicle(vehicle)
                .ride(ride)
                .rideType(rideType)
                .status(RequestStatus.PENDING)
                .pickupName(request.getPickupName())
                .pickupLat(request.getPickupLat())
                .pickupLng(request.getPickupLng())
                .dropName(request.getDropName())
                .dropLat(request.getDropLat())
                .dropLng(request.getDropLng())
                .requestedSeats(request.getRequestedSeats() == null ? 1 : request.getRequestedSeats())
                .estimatedFare(request.getEstimatedFare())
                .build());

        if (driver != null) {
            notificationService.createNotification(driver.getUser(), "New ride request",
                    "A rider requested a " + rideType.name().toLowerCase() + " ride from " + request.getPickupName());
        } else {
            notificationService.broadcastToApprovedDrivers("New ride request",
                    "A rider requested a ride from " + request.getPickupName() + " to " + request.getDropName());
        }
        return rideRequest;
    }

    @Override
    public List<RideRequest> myBookings(User user) {
        return rideRequestRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public UserDashboardResponse dashboard(User user) {
        return UserDashboardResponse.builder()
                .totalBookings(rideRequestRepository.countByUser(user))
                .pendingBookings(rideRequestRepository.countByUserAndStatus(user, RequestStatus.PENDING))
                .acceptedBookings(rideRequestRepository.countByUserAndStatus(user, RequestStatus.ACCEPTED))
                .build();
    }

    private NearbyVehicleResponse mapNearbyVehicle(NearbyVehicleProjection row, BigDecimal lat, BigDecimal lng) {
        double distance = GeoUtil.distanceKm(lat.doubleValue(), lng.doubleValue(),
                row.getLatitude().doubleValue(), row.getLongitude().doubleValue());
        return NearbyVehicleResponse.builder()
                .vehicleId(row.getVehicleId())
                .driverId(row.getDriverId())
                .driverName(row.getDriverName())
                .vehicleType(row.getVehicleType())
                .registrationNumber(row.getRegistrationNumber())
                .seatCapacity(row.getSeatCapacity())
                .bookableSeats(row.getBookableSeats())
                .pricePerKm(row.getPricePerKm())
                .latitude(row.getLatitude())
                .longitude(row.getLongitude())
                .distanceKm(distance)
                .build();
    }
}
