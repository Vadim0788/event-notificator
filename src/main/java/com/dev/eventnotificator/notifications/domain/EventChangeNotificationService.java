package com.dev.eventnotificator.notifications.domain;

import com.dev.eventnotificator.notifications.EventChangeNotificationMapper;
import com.dev.eventnotificator.notifications.api.EventChangeNotificationDTO;
import com.dev.eventnotificator.notifications.db.EventChangeNotificationEntity;
import com.dev.eventnotificator.notifications.db.EventChangeNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventChangeNotificationService {

    private final EventChangeNotificationRepository notificationRepository;
    private final EventChangeNotificationMapper notificationMapper;
    private final EventChangeNotificationMapper eventChangeNotificationMapper;

    public EventChangeNotificationService(EventChangeNotificationRepository notificationRepository, EventChangeNotificationMapper notificationMapper, EventChangeNotificationMapper eventChangeNotificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.eventChangeNotificationMapper = eventChangeNotificationMapper;
    }

    public EventChangeNotification createEventChangeNotification(EventChangeNotification notification) {
        var savedNotification =
                notificationRepository.save(notificationMapper.toEntity(notification));

        return notificationMapper.toDomain(savedNotification);

    }

    public List<EventChangeNotificationDTO> getEventChangeNotificationsByIds(List<Long> eventIds) {
        List<EventChangeNotificationEntity> eventChangeNotificationEntities =
                notificationRepository.findByEventIdIn(eventIds);

        List<EventChangeNotification> eventChangeNotifications = eventChangeNotificationEntities.stream()
                .map(eventChangeNotificationMapper::toDomain)
                .toList();

        return eventChangeNotifications.stream()
                .map(eventChangeNotificationMapper::toDto)
                .toList();
    }
}
