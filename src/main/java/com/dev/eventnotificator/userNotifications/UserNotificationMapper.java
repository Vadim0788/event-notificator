package com.dev.eventnotificator.userNotifications;

import com.dev.eventnotificator.userNotifications.api.UserNotificationDTO;
import com.dev.eventnotificator.userNotifications.db.UserNotificationEntity;
import com.dev.eventnotificator.userNotifications.domain.UserNotification;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationMapper {


    public UserNotification toDomain(UserNotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserNotification(
                entity.getId(),
                entity.getUserId(),
                entity.getEventId(),
                entity.getCreatedAt(),
                entity.isRead()
        );
    }

    public UserNotificationEntity toEntity(UserNotification domain) {
        if (domain == null) {
            return null;
        }
        return new UserNotificationEntity(
                domain.userId(),
                domain.eventId(),
                domain.createdAt(),
                domain.isRead()
        );
    }

    public UserNotificationDTO toDto(UserNotification domain) {
        if (domain == null) {
            return null;
        }
        return new UserNotificationDTO(
                domain.id(),
                domain.userId(),
                domain.eventId(),
                domain.createdAt(),
                domain.isRead()
        );
    }

    public UserNotification toDomain(UserNotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        return new UserNotification(
                dto.id(),
                dto.userId(),
                dto.eventId(),
                dto.createdAt(),
                dto.isRead()
        );
    }
}
