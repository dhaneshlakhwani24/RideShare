package com.dhaneshlakhwani.rideshare.service.impl;

import com.dhaneshlakhwani.rideshare.dto.dashboard.DriverDashboardResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.CreateRideRequest;
import com.dhaneshlakhwani.rideshare.dto.ride.LocationUpdateRequest;
import com.dhaneshlakhwani.rideshare.entity.*;
import com.dhaneshlakhwani.rideshare.repository.DriverProfileRepository;
import com.dhaneshlakhwani.rideshare.repository.RideRepository;
import com.dhaneshlakhwani.rideshare.repository.RideRequestRepository;
import com.dhaneshlakhwani.rideshare.repository.VehicleRepository;
import com.dhaneshlakhwani.rideshare.entity.*;
import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import com.dhaneshlakhwani.rideshare.enums.RequestStatus;
import com.dhaneshlakhwani.rideshare.enums.RideStatus;
import com.dhaneshlakhwani.rideshare.enums.RideType;
import com.dhaneshlakhwani.rideshare.repository.*;
import com.dhaneshlakhwani.rideshare.service.DriverService;
import com.dhaneshlakhwani.rideshare.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverProfileRepository driverProfileRepository;
    private final VehicleRepository vehicleRepository;
    private final RideRepository rideRepository;
    private final RideRequestRepository rideRequestRepository;
    private final NotificationService notificationService;

    @Override
    public DriverProfile getDriverByUser(User user) {
        return driverProfileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Driver profile not found"));
    }

    @Override
    public DriverDashboardResponse dashboard(User user) {
        DriverProfile driver = getDriverByUser(user);
        return DriverDashboardResponse.builder()
                .approvalStatus(driver.getApprovalStatus().name())
                .totalVehicles(vehicleRepository.findByDriver(driver).size())
                .activeSharedRides(
                        rideRepository.countByDriverAndRideTypeAndStatus(driver, RideType.SHARED, RideStatus.SCHEDULED) +
                        rideRepository.countByDriverAndRideTypeAndStatus(driver, RideType.SHARED, RideStatus.ACTIVE))
                .pendingRideRequests(rideRequestRepository.findByDriverAndStatusOrderByCreatedAtDesc(driver, RequestStatus.PENDING).size())
                .build();
    }

    @Override
    @Transactional
    public void updateVehicleLocation(User user, LocationUpdateRequest request) {
        DriverProfile driver = getDriverByUser(user);
        Vehicle vehicle = vehicleRepository.findByDriver(driver).stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        vehicle.setCurrentLatitude(request.getLatitude());
        vehicle.setCurrentLongitude(request.getLongitude());
        if (request.getOnline() != null) {
            vehicle.setOnline(request.getOnline() && driver.getApprovalStatus() == ApprovalStatus.APPROVED);
        }
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public Ride offerSharedRide(User user, CreateRideRequest request) {
        DriverProfile driver = getDriverByUser(user);
        if (driver.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new IllegalStateException("Driver is not approved by superadmin yet");
        }
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        if (!vehicle.getDriver().getId().equals(driver.getId())) {
            throw new IllegalArgumentException("Vehicle does not belong to current driver");
        }
        Ride ride = Ride.builder()
                .driver(driver)
                .vehicle(vehicle)
                .rideType(RideType.SHARED)
                .status(RideStatus.SCHEDULED)
                .originName(request.getOriginName())
                .originLat(request.getOriginLat())
                .originLng(request.getOriginLng())
                .destinationName(request.getDestinationName())
                .destinationLat(request.getDestinationLat())
                .destinationLng(request.getDestinationLng())
                .departureTime(LocalDateTime.parse(request.getDepartureTime()))
                .baseFare(request.getBaseFare())
                .availableSeats(request.getAvailableSeats() == null ? vehicle.getBookableSeats() : request.getAvailableSeats())
                .build();
        return rideRepository.save(ride);
    }

    @Override
    public List<RideRequest> pendingRequests(User user) {
        return rideRequestRepository.findByDriverAndStatusOrderByCreatedAtDesc(getDriverByUser(user), RequestStatus.PENDING);
    }

    @Override
    public List<Ride> myRides(User user) {
        return rideRepository.findByDriverOrderByDepartureTimeDesc(getDriverByUser(user));
    }

    @Override
    @Transactional
    public RideRequest respondToRequest(User user, Long rideRequestId, String action) {
        DriverProfile driver = getDriverByUser(user);
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Ride request not found"));
        if (rideRequest.getDriver() == null || !rideRequest.getDriver().getId().equals(driver.getId())) {
            throw new IllegalArgumentException("This ride request is not assigned to current driver");
        }
        boolean accepted = "ACCEPTED".equalsIgnoreCase(action);
        rideRequest.setStatus(accepted ? RequestStatus.ACCEPTED : RequestStatus.REJECTED);

        if (accepted && rideRequest.getRide() != null) {
            Ride ride = rideRequest.getRide();
            int seatsLeft = ride.getAvailableSeats() - (rideRequest.getRequestedSeats() == null ? 1 : rideRequest.getRequestedSeats());
            if (seatsLeft < 0) throw new IllegalStateException("Not enough available seats");
            ride.setAvailableSeats(seatsLeft);
            ride.setStatus(RideStatus.ACTIVE);
            rideRepository.save(ride);
        }

        rideRequestRepository.save(rideRequest);
        notificationService.createNotification(rideRequest.getUser(),
                accepted ? "Ride accepted" : "Ride rejected",
                accepted ? "Your ride has been accepted by the driver." : "Your ride request was rejected.");
        return rideRequest;
    }
}
