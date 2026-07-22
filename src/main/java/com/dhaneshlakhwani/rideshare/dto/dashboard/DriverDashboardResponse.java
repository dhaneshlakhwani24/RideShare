package com.dhaneshlakhwani.rideshare.dto.dashboard;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDashboardResponse {
    private String approvalStatus;
    private long totalVehicles;
    private long activeSharedRides;
    private long pendingRideRequests;
}
