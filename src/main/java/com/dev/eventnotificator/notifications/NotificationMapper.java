package com.dev.eventnotificator.notifications;

import com.dev.eventnotificator.notifications.api.NotificationDTO;
import com.dev.eventnotificator.notifications.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toDomain(com.dev.eventnotificator.notifications.db.Notification entity) {
        if (entity == null) {
            return null;
        }
        return new Notification(
                entity.getEventId(),
                entity.getOwnerId(),
                entity.getCreatedAt(),
                entity.getMessageType(),
                entity.getChangedById(),
                entity.getName(),
                entity.getMaxPlaces(),
                entity.getDate(),
                entity.getCost(),
                entity.getDuration(),
                entity.getLocationId(),
                entity.getSubscribersId()
        );
    }

    public com.dev.eventnotificator.notifications.db.Notification toEntity(Notification event) {
        if (event == null) {
            return null;
        }
        com.dev.eventnotificator.notifications.db.Notification entity = new com.dev.eventnotificator.notifications.db.Notification();
        entity.setEventId(event.eventId());
        entity.setOwnerId(event.ownerId());
        entity.setCreatedAt(event.createdAt());
        entity.setMessageType(event.MessageType());
        entity.setChangedById(event.changedById());
        entity.setName(event.name());
        entity.setMaxPlaces(event.maxPlaces());
        entity.setDate(event.date());
        entity.setCost(event.cost());
        entity.setDuration(event.duration());
        entity.setLocationId(event.locationId());
        entity.setSubscribersId(event.subscribersId());
        return entity;
    }


    public NotificationDTO toDto(Notification event) {
        if (event == null) {
            return null;
        }
        return new NotificationDTO(
                event.eventId(),
                event.ownerId(),
                event.MessageType(),
                event.changedById(),
                event.name(),
                event.maxPlaces(),
                event.date(),
                event.cost(),
                event.duration(),
                event.locationId(),
                event.subscribersId()
        );
    }

}