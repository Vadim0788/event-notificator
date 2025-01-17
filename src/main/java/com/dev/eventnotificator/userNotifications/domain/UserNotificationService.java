package com.dev.eventnotificator.userNotifications.domain;

import com.dev.eventnotificator.notifications.db.Notification;
import com.dev.eventnotificator.notifications.db.NotificationRepository;
import com.dev.eventnotificator.userNotifications.UserNotificationMapper;
import com.dev.eventnotificator.userNotifications.db.UserNotificationEntity;
import com.dev.eventnotificator.userNotifications.db.UserNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserNotificationService {

    private static final Logger log = LoggerFactory.getLogger(UserNotificationService.class);
    private final UserNotificationRepository userNotificationRepository;
    private final UserNotificationMapper userNotificationMapper;
    private final NotificationRepository notificationRepository;


    public UserNotificationService(
            UserNotificationRepository userNotificationRepository,
            UserNotificationMapper userNotificationMapper,
            NotificationRepository notificationRepository
    ) {
        this.userNotificationRepository = userNotificationRepository;
        this.userNotificationMapper = userNotificationMapper;
        this.notificationRepository = notificationRepository;
    }

    public void saveAll(List<UserNotification> userNotifications, Notification notificationEntity) {

        List<UserNotificationEntity> userNotificationEntities = userNotifications.stream()
                .map(userNotificationMapper::toEntity)
                .collect(Collectors.toList());
        userNotificationEntities.forEach(userNotificationEntity ->
                userNotificationEntity.setEventChangeNotification(notificationEntity)
        );

        userNotificationRepository.saveAll(userNotificationEntities);
    }

    public List<UserNotification> getUnreadNotifications(Long userId) {
        log.info("Fetching unread notifications for userId={}", userId);
        return userNotificationRepository.findByUserIdAndReadIsFalse(userId)
                .stream()
                .map(userNotificationMapper::toDomain)
                .toList();
    }

    @Transactional
    public void markNotificationsAsRead(Long userId, List<Long> notificationIds) {

        List<Notification> eventChangeNotifications =
                notificationRepository.findAllById(notificationIds);

        if (eventChangeNotifications.isEmpty()) {
            log.info("No events found for notification IDs: {}", notificationIds);
            return;
        }


        List<UserNotificationEntity> notifications =
                userNotificationRepository.findByUserIdAndEventChangeNotificationIn(userId, eventChangeNotifications);

        if (notifications.isEmpty()) {
            log.info("No notifications found for userId: {} and notificationIds: {}", userId, notificationIds);
            return;
        }

        notifications.forEach(notification -> notification.setRead(true));

        userNotificationRepository.saveAll(notifications);

        log.info("Notifications marked as read for userId: {} and notificationIds: {}", userId, notificationIds);
    }

    @Transactional
    public void deleteOldNotificationsAndEvents(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        log.info("Deleting notifications and related events older than {}", cutoffDate);

        notificationRepository.deleteAllByCreatedAtBefore(cutoffDate);

    }
}
