package com.dev.eventnotificator.notifications.api;

import com.dev.eventnotificator.notifications.FieldChange;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record NotificationDTO(
        Long eventId,
        Long ownerId,
        String messageType,
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
