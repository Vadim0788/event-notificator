package com.dev.eventnotificator.userNotifications.domain;

import com.dev.eventnotificator.notifications.domain.EventChangeNotification;

import java.time.LocalDateTime;

public record UserNotification(
        Long id,
        Long userId,
        EventChangeNotification eventChangeNotification,
        LocalDateTime createdAt,
        boolean isRead
) {
}