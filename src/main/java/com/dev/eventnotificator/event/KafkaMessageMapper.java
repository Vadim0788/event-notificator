package com.dev.eventnotificator.event;

import com.dev.eventnotificator.notifications.domain.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class KafkaMessageMapper {
    public Notification toDomain(EventKafkaMessage message) {
        if (message == null) {
            return null;
        }
        return new Notification(
                message.eventId(),
                message.ownerId(),
                LocalDateTime.now(),
                message.messageType().name(),
                message.changedById(),
                message.name(),
                message.maxPlaces(),
                message.date(),
                message.cost(),
                message.duration(),
                message.locationId(),
                message.subscribersId()
        );
    }
}
