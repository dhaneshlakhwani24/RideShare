package com.dhaneshlakhwani.rideshare.repository;

import com.dhaneshlakhwani.rideshare.entity.Notification;
import com.dhaneshlakhwani.rideshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop10ByUserOrderByCreatedAtDesc(User user);
}
