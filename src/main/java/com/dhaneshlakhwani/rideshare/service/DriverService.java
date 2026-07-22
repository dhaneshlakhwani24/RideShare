package com.dhaneshlakhwani.rideshare.service;

import com.dhaneshlakhwani.rideshare.dto.dashboard.DriverDashboardResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.CreateRideRequest;
import com.dhaneshlakhwani.rideshare.dto.ride.LocationUpdateRequest;
import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.entity.Ride;
import com.dhaneshlakhwani.rideshare.entity.RideRequest;
import com.dhaneshlakhwani.rideshare.entity.User;

import java.util.List;

public interface DriverService {
    DriverProfile getDriverByUser(User user);
    DriverDashboardResponse dashboard(User user);
    void updateVehicleLocation(User user, LocationUpdateRequest request);
    Ride offerSharedRide(User user, CreateRideRequest request);
    List<RideRequest> pendingRequests(User user);
    List<Ride> myRides(User user);
    RideRequest respondToRequest(User user, Long rideRequestId, String action);
}
