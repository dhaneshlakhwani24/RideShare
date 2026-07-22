package com.dhaneshlakhwani.rideshare.controller.api;

import com.dhaneshlakhwani.rideshare.dto.ApiResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.RideRequestDTO;
import com.dhaneshlakhwani.rideshare.dto.dashboard.UserDashboardResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.NearbyVehicleResponse;
import com.dhaneshlakhwani.rideshare.dto.ride.RideRequestCreateRequest;
import com.dhaneshlakhwani.rideshare.entity.RideRequest;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.Role;
import com.dhaneshlakhwani.rideshare.mapper.RideRequestMapper;
import com.dhaneshlakhwani.rideshare.projection.SharedRideProjection;
import com.dhaneshlakhwani.rideshare.service.AuthService;
import com.dhaneshlakhwani.rideshare.service.UserService;
import com.dhaneshlakhwani.rideshare.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public ApiResponse<UserDashboardResponse> dashboard(HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.USER);
        return ApiResponse.<UserDashboardResponse>builder().success(true).data(userService.dashboard(user)).build();
    }

    @GetMapping("/nearby-vehicles")
    public ApiResponse<List<NearbyVehicleResponse>> nearbyVehicles(@RequestParam BigDecimal lat,
                                                                   @RequestParam BigDecimal lng,
                                                                   @RequestParam(defaultValue = "5") BigDecimal radiusKm,
                                                                   HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.USER);
        return ApiResponse.<List<NearbyVehicleResponse>>builder().success(true)
                .data(userService.findNearbyVehicles(lat, lng, radiusKm).join()).build();
    }

    @GetMapping("/shared-rides")
    public ApiResponse<List<SharedRideProjection>> sharedRides(@RequestParam BigDecimal originLat,
                                                               @RequestParam BigDecimal originLng,
                                                               @RequestParam BigDecimal destinationLat,
                                                               @RequestParam BigDecimal destinationLng,
                                                               @RequestParam(defaultValue = "5") BigDecimal radiusKm,
                                                               HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.USER);
        return ApiResponse.<List<SharedRideProjection>>builder().success(true)
                .data(userService.searchSharedRides(originLat, originLng, destinationLat, destinationLng, radiusKm).join()).build();
    }

    @PostMapping("/ride-requests")
    public ApiResponse<RideRequest> createRideRequest(@RequestBody RideRequestCreateRequest request, HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.USER);
        return ApiResponse.<RideRequest>builder().success(true).message("Ride request created")
                .data(userService.createRideRequest(user, request)).build();
    }

    @GetMapping("/my-bookings")
    public ApiResponse<List<RideRequestDTO>> myBookings(HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.USER);
        List<RideRequestDTO> bookings = userService.myBookings(user)
                .stream()
                .map(RideRequestMapper::toDTO)
                .toList();
        return ApiResponse.<List<RideRequestDTO>>builder().success(true).data(bookings).build();
    }
}
