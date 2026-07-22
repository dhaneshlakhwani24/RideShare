package com.dhaneshlakhwani.rideshare.controller.api;

import com.dhaneshlakhwani.rideshare.dto.ApiResponse;
import com.dhaneshlakhwani.rideshare.dto.admin.ApprovalDecisionRequest;
import com.dhaneshlakhwani.rideshare.dto.dashboard.AdminDashboardResponse;
import com.dhaneshlakhwani.rideshare.entity.DriverApprovalRequest;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.Role;
import com.dhaneshlakhwani.rideshare.service.AdminService;
import com.dhaneshlakhwani.rideshare.service.AuthService;
import com.dhaneshlakhwani.rideshare.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {
    private final AuthService authService;
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ApiResponse<AdminDashboardResponse> dashboard(HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.SUPERADMIN);
        return ApiResponse.<AdminDashboardResponse>builder().success(true).data(adminService.dashboard()).build();
    }

    @GetMapping("/pending-approvals")
    public ApiResponse<List<DriverApprovalRequest>> pendingApprovals(HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.SUPERADMIN);
        return ApiResponse.<List<DriverApprovalRequest>>builder().success(true).data(adminService.pendingApprovals()).build();
    }

    @PostMapping("/driver-approvals/{driverId}/decision")
    public ApiResponse<Void> decision(@PathVariable Long driverId,
                                      @RequestBody ApprovalDecisionRequest request,
                                      HttpSession session) {
        User user = authService.getCurrentUser(session);
        SessionUtil.requireRole(user, Role.SUPERADMIN);
        adminService.decideApproval(user, driverId, request.getAction(), request.getRemarks());
        return ApiResponse.<Void>builder().success(true).message("Driver approval updated").build();
    }
}
