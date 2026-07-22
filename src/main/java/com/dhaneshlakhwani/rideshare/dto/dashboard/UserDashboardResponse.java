package com.dhaneshlakhwani.rideshare.dto.dashboard;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardResponse {
    private long totalBookings;
    private long pendingBookings;
    private long acceptedBookings;
}
