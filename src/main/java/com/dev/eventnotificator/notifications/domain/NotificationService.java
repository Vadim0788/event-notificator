package com.dev.eventnotificator.notifications.domain;

import com.dev.eventnotificator.notifications.NotificationMapper;

import com.dev.eventnotificator.notifications.db.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @Transactional
    public com.dev.eventnotificator.notifications.db.Notification findOrCreateEventChangeNotification(Notification notification) {

        var entity = notificationRepository.findById(notification.eventId())
                .orElseGet(() -> notificationRepository.save(notificationMapper.toEntity(notification)));
        entity.getSubscribersId().size();
        return entity;

    }
}
