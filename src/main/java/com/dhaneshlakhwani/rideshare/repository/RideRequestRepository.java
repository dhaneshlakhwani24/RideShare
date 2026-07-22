package com.dhaneshlakhwani.rideshare.repository;

import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.entity.RideRequest;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    List<RideRequest> findByUserOrderByCreatedAtDesc(User user);
    List<RideRequest> findByDriverAndStatusOrderByCreatedAtDesc(DriverProfile driver, RequestStatus status);
    long countByUser(User user);
    long countByUserAndStatus(User user, RequestStatus status);
}
