package com.dhaneshlakhwani.rideshare.controller.api;

import com.dhaneshlakhwani.rideshare.dto.ApiResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.RideRequestDTO;
import com.dhaneshlakhwani.rideshare.dto.dashboard.DriverDashboardResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.CreateRideRequest;
import com.dhaneshlakhwani.rideshare.dto.ride.LocationUpdateRequest;
import com.dhaneshlakhwani.rideshare.dto.ride.RideDTO;
import com.dhaneshlakhwani.rideshare.dto.ride.RideRequestResponseRequest;
import com.dhaneshlakhwani.rideshare.entity.Ride;
import com.dhaneshlakhwani.rideshare.entity.RideRequest;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.Role;
import com.dhaneshlakhwani.rideshare.mapper.RideMapper;
import com.dhaneshlakhwani.rideshare.mapper.RideRequestMapper;
import com.dhaneshlakhwani.rideshare.service.AuthService;
import com.dhaneshlakhwani.rideshare.service.DriverService;
import com.dhaneshlakhwani.rideshare.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverApiController {
    private final AuthService authService;
    private final DriverService driverService;

    @GetMapping("/dashboard")
    public ApiResponse<DriverDashboardResponse> dashboard(HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.DRIVER);
        return ApiResponse.<DriverDashboardResponse>builder().success(true).data(driverService.dashboard(user)).build();
    }

    @PutMapping("/location")
    public ApiResponse<Void> updateLocation(@RequestBody LocationUpdateRequest request, HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.DRIVER);
        driverService.updateVehicleLocation(user, request);
        return ApiResponse.<Void>builder().success(true).message("Location updated").build();
    }

    @PostMapping("/rides/offer-shared")
    public ApiResponse<Ride> offerSharedRide(@RequestBody CreateRideRequest request, HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.DRIVER);
        return ApiResponse.<Ride>builder().success(true).data(driverService.offerSharedRide(user, request)).build();
    }

    @GetMapping("/pending-requests")
    public ApiResponse<List<RideRequestDTO>> pendingRequests(HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.DRIVER);

        List<RideRequestDTO> pendings = driverService.pendingRequests(user)
                .stream()
                .map(RideRequestMapper::toDTO)
                .toList();
        return ApiResponse.<List<RideRequestDTO>>builder().success(true).data(pendings).build();
    }

    @GetMapping("/my-rides")
    public ApiResponse<List<RideDTO>> myRides(HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.DRIVER);

        List<RideDTO> rides = driverService.myRides(user)
                .stream()
                .map(RideMapper::toDTO)
                .toList();
        return ApiResponse.<List<RideDTO>>builder().success(true).data(rides).build();
    }

    @PostMapping("/ride-requests/{rideRequestId}/respond")
    public ApiResponse<RideRequest> respond(@PathVariable Long rideRequestId,
                                            @RequestBody RideRequestResponseRequest request,
                                            HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.DRIVER);
        return ApiResponse.<RideRequest>builder().success(true)
                .data(driverService.respondToRequest(user, rideRequestId, request.getAction())).build();
    }
}
