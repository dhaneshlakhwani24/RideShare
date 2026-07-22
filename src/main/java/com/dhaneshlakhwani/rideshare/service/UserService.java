package com.dhaneshlakhwani.rideshare.service;

import com.dhaneshlakhwani.rideshare.dto.dashboard.UserDashboardResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.NearbyVehicleResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.RideRequestCreateRequest;
import com.dhaneshlakhwani.rideshare.entity.RideRequest;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.projection.SharedRideProjection;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<List<NearbyVehicleResponse>> findNearbyVehicles(BigDecimal lat, BigDecimal lng, BigDecimal radiusKm);
    CompletableFuture<List<SharedRideProjection>> searchSharedRides(BigDecimal originLat, BigDecimal originLng,
                                                                    BigDecimal destinationLat, BigDecimal destinationLng,
                                                                    BigDecimal radiusKm);
    RideRequest createRideRequest(User user, RideRequestCreateRequest request);
    List<RideRequest> myBookings(User user);
    UserDashboardResponse dashboard(User user);
}
