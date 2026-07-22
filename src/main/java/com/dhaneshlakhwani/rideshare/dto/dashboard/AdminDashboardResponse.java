package com.dhaneshlakhwani.rideshare.dto.dashboard;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {
    private long totalUsers;
    private long totalDrivers;
    private long pendingApprovals;
    private long activeVehicles;
}
