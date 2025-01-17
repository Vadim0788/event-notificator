package com.dev.eventnotificator.userNotifications.domain;

import com.dev.eventnotificator.notifications.domain.Notification;

public record UserNotification(
        Long id,
        Long userId,
        Notification notification,
        boolean isRead
) {
}