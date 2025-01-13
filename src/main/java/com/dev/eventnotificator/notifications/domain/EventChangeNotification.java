package com.dev.eventnotificator.notifications.domain;

import com.dev.eventnotificator.notifications.FieldChange;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record EventChangeNotification(
        Long eventId,
        Long ownerId,
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