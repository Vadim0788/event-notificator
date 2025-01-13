package com.dev.eventnotificator.notifications;

import com.dev.eventnotificator.notifications.api.EventChangeNotificationDTO;
import com.dev.eventnotificator.notifications.db.EventChangeNotificationEntity;
import com.dev.eventnotificator.notifications.domain.EventChangeNotification;
import org.springframework.stereotype.Component;

@Component
public class EventChangeNotificationMapper {

    public EventChangeNotification toDomain(EventChangeNotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new EventChangeNotification(
                entity.getEventId(),
                entity.getOwnerId(),
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

    public EventChangeNotificationEntity toEntity(EventChangeNotification event) {
        if (event == null) {
            return null;
        }
        EventChangeNotificationEntity entity = new EventChangeNotificationEntity();
        entity.setEventId(event.eventId());
        entity.setOwnerId(event.ownerId());
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


    public EventChangeNotificationDTO toDto(EventChangeNotification event) {
        if (event == null) {
            return null;
        }
        return new EventChangeNotificationDTO(
                event.eventId(),
                event.ownerId(),
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

    public EventChangeNotification toDomain(EventChangeNotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        return new EventChangeNotification(
                dto.eventId(),
                dto.ownerId(),
                dto.changedById(),
                dto.name(),
                dto.maxPlaces(),
                dto.date(),
                dto.cost(),
                dto.duration(),
                dto.locationId(),
                dto.subscribersId()
        );
    }

}