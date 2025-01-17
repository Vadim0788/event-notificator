package com.dev.eventnotificator.userNotifications;

import com.dev.eventnotificator.notifications.NotificationMapper;
import com.dev.eventnotificator.notifications.db.Notification;
import com.dev.eventnotificator.userNotifications.db.UserNotificationEntity;
import com.dev.eventnotificator.userNotifications.domain.UserNotification;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationMapper {

    private final NotificationMapper notificationMapper;

    public UserNotificationMapper(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    public UserNotification toDomain(UserNotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserNotification(
                entity.getId(),
                entity.getUserId(),
                notificationMapper.toDomain(entity.getEventChangeNotification()),
                entity.isRead()
        );
    }

    public UserNotificationEntity toEntity(UserNotification domain) {
        if (domain == null) {
            return null;
        }
        Notification notification =
                notificationMapper.toEntity(domain.notification());
        return new UserNotificationEntity(
                domain.userId(),
                notification,
                domain.isRead()
        );
    }

}
