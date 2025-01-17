package com.dev.eventnotificator.userNotifications.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record NotificationIdsRequest(
        @NotNull(message = "notificationIds cannot be null")
        List<
                @NotNull(message = "Each notificationId must not be null")
                @Positive(message = "Each notificationId must be greater than 0")
                        Long
                > notificationIds
) {
}
