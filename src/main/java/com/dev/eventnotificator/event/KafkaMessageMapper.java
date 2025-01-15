package com.dev.eventnotificator.event;

import com.dev.eventnotificator.notifications.domain.EventChangeNotification;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageMapper {
    public EventChangeNotification toDomain(EventKafkaMessage message) {
        if (message == null) {
            return null;
        }
        return new EventChangeNotification(
                message.eventId(),
                message.ownerId(),
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
