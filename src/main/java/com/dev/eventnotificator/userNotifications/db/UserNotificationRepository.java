package com.dev.eventnotificator.userNotifications.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, Long> {

    List<UserNotificationEntity> findAllByUserId(Long userId);

    List<UserNotificationEntity> findByUserIdAndEventIdIn(Long userId, List<Long> eventIds);

    @Transactional
    int deleteByCreatedAtBefore(LocalDateTime cutoffDate);

    List<UserNotificationEntity> findByUserIdAndReadIsFalse(Long userId);


}
