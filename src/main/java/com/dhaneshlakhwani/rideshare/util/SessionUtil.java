package com.dhaneshlakhwani.rideshare.util;

import com.dhaneshlakhwani.rideshare.entity.User;
import com.dhaneshlakhwani.rideshare.enums.Role;

public final class SessionUtil {
    private SessionUtil() {}
    public static void requireRole(User user, Role role) {
        if (user.getRole() != role) {
            throw new IllegalStateException("Access denied for role: " + user.getRole());
        }
    }
}
