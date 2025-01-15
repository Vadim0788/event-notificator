package com.dev.eventnotificator.userNotifications;

import com.dev.eventnotificator.notifications.EventChangeNotificationMapper;
import com.dev.eventnotificator.notifications.db.EventChangeNotificationEntity;
import com.dev.eventnotificator.userNotifications.db.UserNotificationEntity;
import com.dev.eventnotificator.userNotifications.domain.UserNotification;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationMapper {

    private final EventChangeNotificationMapper eventChangeNotificationMapper;

    public UserNotificationMapper(EventChangeNotificationMapper eventChangeNotificationMapper) {
        this.eventChangeNotificationMapper = eventChangeNotificationMapper;
    }

    public UserNotification toDomain(UserNotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserNotification(
                entity.getId(),
                entity.getUserId(),
                eventChangeNotificationMapper.toDomain(entity.getEventChangeNotification()),
                entity.getCreatedAt(),
                entity.isRead()
        );
    }

    public UserNotificationEntity toEntity(UserNotification domain) {
        if (domain == null) {
            return null;
        }
        EventChangeNotificationEntity eventChangeNotificationEntity =
                eventChangeNotificationMapper.toEntity(domain.eventChangeNotification());
        return new UserNotificationEntity(
                domain.userId(),
                eventChangeNotificationEntity,
                domain.createdAt(),
                domain.isRead()
        );
    }

}
