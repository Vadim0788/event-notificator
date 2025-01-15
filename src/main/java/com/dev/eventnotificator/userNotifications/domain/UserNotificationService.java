package com.dev.eventnotificator.userNotifications.domain;

import com.dev.eventnotificator.notifications.db.EventChangeNotificationEntity;
import com.dev.eventnotificator.notifications.db.EventChangeNotificationRepository;
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
    private final EventChangeNotificationRepository eventChangeNotificationRepository;


    public UserNotificationService(
            UserNotificationRepository userNotificationRepository,
            UserNotificationMapper userNotificationMapper,
            EventChangeNotificationRepository eventChangeNotificationRepository
    ) {
        this.userNotificationRepository = userNotificationRepository;
        this.userNotificationMapper = userNotificationMapper;
        this.eventChangeNotificationRepository = eventChangeNotificationRepository;
    }

    public void saveAll(List<UserNotification> userNotifications, EventChangeNotificationEntity notificationEntity) {

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

    public List<UserNotification> findNotificationsByUserId(Long usrId) {
        List<UserNotificationEntity> userNotificationEntities = userNotificationRepository.findAllByUserId(usrId);

        return userNotificationEntities.stream()
                .map(userNotificationMapper::toDomain)
                .toList();
    }

    @Transactional
    public void markNotificationsAsRead(Long userId, List<Long> notificationIds) {

        List<EventChangeNotificationEntity> eventChangeNotifications =
                eventChangeNotificationRepository.findAllById(notificationIds);

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

    public int deleteNotificationsOlderThanDays(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        log.info("Deleting notifications older than {}", cutoffDate);
        int deletedCount = userNotificationRepository.deleteByCreatedAtBefore(cutoffDate);
        log.info("{} notifications deleted.", deletedCount);
        return deletedCount;
    }

    @Transactional
    public int deleteOldNotificationsAndEvents(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        log.info("Deleting notifications and related events older than {}", cutoffDate);

        List<UserNotificationEntity> oldNotifications =
                userNotificationRepository.findByCreatedAtBefore(cutoffDate);

        List<EventChangeNotificationEntity> relatedEvents = oldNotifications.stream()
                .map(UserNotificationEntity::getEventChangeNotification)
                .distinct()
                .toList();

        eventChangeNotificationRepository.deleteAll(relatedEvents);

        log.info("Deleted {} notifications and their related events.", oldNotifications.size());

        return oldNotifications.size();
    }
}
