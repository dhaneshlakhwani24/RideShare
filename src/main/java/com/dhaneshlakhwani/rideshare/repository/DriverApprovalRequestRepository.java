package com.dhaneshlakhwani.rideshare.repository;

import com.dhaneshlakhwani.rideshare.entity.DriverApprovalRequest;
import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverApprovalRequestRepository extends JpaRepository<DriverApprovalRequest, Long> {
    List<DriverApprovalRequest> findByStatusOrderByCreatedAtAsc(ApprovalStatus status);
    List<DriverApprovalRequest> findByDriverOrderByCreatedAtDesc(DriverProfile driver);
}
