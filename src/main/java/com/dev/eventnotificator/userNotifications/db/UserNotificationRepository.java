package com.dev.eventnotificator.userNotifications.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.eventnotificator.notifications.db.EventChangeNotificationEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, Long> {

    List<UserNotificationEntity> findAllByUserId(Long userId);

    List<UserNotificationEntity> findByUserIdAndEventChangeNotificationIn(Long userId, List<EventChangeNotificationEntity> eventChangeNotifications);

    @Transactional
    int deleteByCreatedAtBefore(LocalDateTime cutoffDate);

    List<UserNotificationEntity> findByUserIdAndReadIsFalse(Long userId);


    List<UserNotificationEntity> findByCreatedAtBefore(LocalDateTime cutoffDate);
}
