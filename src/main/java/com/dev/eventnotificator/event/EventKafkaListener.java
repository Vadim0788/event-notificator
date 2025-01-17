package com.dev.eventnotificator.event;

import com.dev.eventnotificator.notifications.NotificationMapper;
import com.dev.eventnotificator.notifications.domain.NotificationService;
import com.dev.eventnotificator.userNotifications.domain.UserNotification;
import com.dev.eventnotificator.userNotifications.domain.UserNotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(EventKafkaListener.class);
    private final NotificationService notificationService;
    private final UserNotificationService userNotificationService;
    private final KafkaMessageMapper kafkaMessageMapper;
    private final NotificationMapper notificationMapper;

    public EventKafkaListener(NotificationService notificationService, UserNotificationService userNotificationService, KafkaMessageMapper kafkaMessageMapper, NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        this.kafkaMessageMapper = kafkaMessageMapper;
        this.notificationMapper = notificationMapper;
    }

    @KafkaListener(topics = "event-topic", groupId = "event-notificator-group")
    public void listenEvents(ConsumerRecord<Long, EventKafkaMessage> record) {

        log.info("Получено событие: {}", record.value());
        var eventChangeNotification = kafkaMessageMapper.toDomain(record.value());
        var notificationEntity = notificationService.findOrCreateEventChangeNotification(eventChangeNotification);
        var notification = notificationMapper.toDomain(notificationEntity);

        log.info("Событие сохранено: {}", notification);

        List<UserNotification> userNotifications = notification.subscribersId().stream()
                .map(subscriberId -> new UserNotification(
                        null,
                        subscriberId,
                        notification,
                        false
                ))
                .toList();

        userNotificationService.saveAll(userNotifications, notificationEntity);
    }
}

