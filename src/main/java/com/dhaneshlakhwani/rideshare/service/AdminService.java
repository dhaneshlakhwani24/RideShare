package com.dhaneshlakhwani.rideshare.service;

import com.dhaneshlakhwani.rideshare.dto.dashboard.AdminDashboardResponse;
import com.dhaneshlakhwani.rideshare.entity.DriverApprovalRequest;
import com.dhaneshlakhwani.rideshare.entity.User;

import java.util.List;

public interface AdminService {
    AdminDashboardResponse dashboard();
    List<DriverApprovalRequest> pendingApprovals();
    void decideApproval(User adminUser, Long driverId, String action, String remarks);
}
