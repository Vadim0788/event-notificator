package com.dev.eventnotificator.userNotifications.domain;

import java.time.LocalDateTime;

public record UserNotification(
        Long id,
        Long userId,
        Long eventId,
        LocalDateTime createdAt,
        boolean isRead
) {
}