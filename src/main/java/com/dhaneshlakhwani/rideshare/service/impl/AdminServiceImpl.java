package com.dhaneshlakhwani.rideshare.service.impl;

import com.dhaneshlakhwani.rideshare.dto.dashboard.AdminDashboardResponse;
import com.dhaneshlakhwani.rideshare.entity.DriverApprovalRequest;
import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import com.dhaneshlakhwani.rideshare.enums.Role;
import com.dhaneshlakhwani.rideshare.repository.DriverApprovalRequestRepository;
import com.dhaneshlakhwani.rideshare.repository.DriverProfileRepository;
import com.dhaneshlakhwani.rideshare.repository.UserRepository;
import com.dhaneshlakhwani.rideshare.repository.VehicleRepository;
import com.dhaneshlakhwani.rideshare.repository.*;
import com.dhaneshlakhwani.rideshare.service.AdminService;
import com.dhaneshlakhwani.rideshare.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final DriverApprovalRequestRepository driverApprovalRequestRepository;
    private final VehicleRepository vehicleRepository;
    private final NotificationService notificationService;

    @Override
    public AdminDashboardResponse dashboard() {
        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.countByRole(Role.USER))
                .totalDrivers(userRepository.countByRole(Role.DRIVER))
                .pendingApprovals(driverProfileRepository.countByApprovalStatus(ApprovalStatus.PENDING))
                .activeVehicles(vehicleRepository.countByOnlineTrue())
                .build();
    }

    @Override
    public List<DriverApprovalRequest> pendingApprovals() {
        return driverApprovalRequestRepository.findByStatusOrderByCreatedAtAsc(ApprovalStatus.PENDING);
    }

    @Override
    @Transactional
    public void decideApproval(User adminUser, Long driverId, String action, String remarks) {
        DriverProfile driver = driverProfileRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));
        ApprovalStatus status = "APPROVED".equalsIgnoreCase(action) ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED;
        driver.setApprovalStatus(status);
        driver.setApprovedBy(adminUser.getId());
        driver.setApprovalRemarks(remarks);
        driverProfileRepository.save(driver);

        driverApprovalRequestRepository.save(DriverApprovalRequest.builder()
                .driver(driver)
                .status(status)
                .remarks(remarks)
                .reviewedBy(adminUser.getId())
                .build());

        notificationService.createNotification(driver.getUser(),
                "Compliance update", "Your driver verification status is now: " + status.name());
    }
}
