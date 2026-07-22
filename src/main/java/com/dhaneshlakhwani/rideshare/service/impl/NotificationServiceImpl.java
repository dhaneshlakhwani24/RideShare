package com.dhaneshlakhwani.rideshare.service.impl;

import com.dhaneshlakhwani.rideshare.entity.DriverProfile;
import com.dhaneshlakhwani.rideshare.entity.Notification;
import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import com.dhaneshlakhwani.rideshare.repository.DriverProfileRepository;
import com.dhaneshlakhwani.rideshare.repository.NotificationRepository;
import com.dhaneshlakhwani.rideshare.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final DriverProfileRepository driverProfileRepository;

    @Override
    @Transactional
    public void createNotification(User user, String title, String message) {
        notificationRepository.save(Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .readFlag(false)
                .build());
    }

    @Override
    @Async
    @Transactional
    public void broadcastToApprovedDrivers(String title, String message) {
        for (DriverProfile driver : driverProfileRepository.findByApprovalStatus(ApprovalStatus.APPROVED)) {
            createNotification(driver.getUser(), title, message);
        }
    }
}
