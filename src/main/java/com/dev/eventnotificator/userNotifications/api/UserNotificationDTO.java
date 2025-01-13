package com.dev.eventnotificator.userNotifications.api;

import java.time.LocalDateTime;

public record UserNotificationDTO(
        Long id,
        Long userId,
        Long eventId,
        LocalDateTime createdAt,
        boolean isRead
) {
}