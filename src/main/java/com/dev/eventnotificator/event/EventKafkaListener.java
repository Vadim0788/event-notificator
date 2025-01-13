package com.dev.eventnotificator.event;

import com.dev.eventnotificator.notifications.domain.EventChangeNotificationService;
import com.dev.eventnotificator.userNotifications.domain.UserNotification;
import com.dev.eventnotificator.userNotifications.domain.UserNotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(EventKafkaListener.class);
    private final EventChangeNotificationService notificationService;
    private final UserNotificationService userNotificationService;
    private final KafkaMessageMapper kafkaMessageMapper;

    public EventKafkaListener(EventChangeNotificationService notificationService, UserNotificationService userNotificationService, KafkaMessageMapper kafkaMessageMapper) {
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        this.kafkaMessageMapper = kafkaMessageMapper;
    }

    @KafkaListener(topics = "event-topic", groupId = "event-notificator-group")
    public void listenEvents(ConsumerRecord<Long, EventKafkaMessage> record) {

        log.info("Получено событие: {}", record.value());
        var eventChangeNotification = kafkaMessageMapper.toDomain(record.value());

        var notification = notificationService.createEventChangeNotification(eventChangeNotification);
        log.info("Событие сохранено: {}", notification);

        List<UserNotification> userNotifications = notification.subscribersId().stream()
                .map(subscriberId -> new UserNotification(
                        null,
                        subscriberId,
                        notification.eventId(),
                        LocalDateTime.now(),
                        false // уведомление не прочитано
                ))
                .toList();

        userNotificationService.saveAll(userNotifications);
    }
}

