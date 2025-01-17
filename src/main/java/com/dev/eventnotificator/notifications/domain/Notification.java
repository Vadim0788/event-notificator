package com.dev.eventnotificator.notifications.domain;

import com.dev.eventnotificator.notifications.FieldChange;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public record Notification(
        Long eventId,
        Long ownerId,
        LocalDateTime createdAt,
        String MessageType,
        Long changedById,
        FieldChange<String> name,
        FieldChange<Long> maxPlaces,
        FieldChange<OffsetDateTime> date,
        FieldChange<BigDecimal> cost,
        FieldChange<Integer> duration,
        FieldChange<Long> locationId,
        List<Long> subscribersId
) {
}