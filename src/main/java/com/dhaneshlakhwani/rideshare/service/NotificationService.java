package com.dhaneshlakhwani.rideshare.service;

import com.dhaneshlakhwani.rideshare.entity.User;

public interface NotificationService {
    void createNotification(User user, String title, String message);
    void broadcastToApprovedDrivers(String title, String message);
}
