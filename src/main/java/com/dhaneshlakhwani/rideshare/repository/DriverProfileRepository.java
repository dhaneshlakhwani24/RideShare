package com.dhaneshlakhwani.rideshare.repository;

import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverProfileRepository extends JpaRepository<DriverProfile, Long> {
    Optional<DriverProfile> findByUser(User user);
    long countByApprovalStatus(ApprovalStatus approvalStatus);
    List<DriverProfile> findByApprovalStatus(ApprovalStatus approvalStatus);
}
