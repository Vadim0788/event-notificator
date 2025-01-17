package com.dev.eventnotificator.userNotifications.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.eventnotificator.notifications.db.Notification;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, Long> {

    List<UserNotificationEntity> findAllByUserId(Long userId);

    List<UserNotificationEntity> findByUserIdAndEventChangeNotificationIn(Long userId, List<Notification> eventChangeNotifications);


    List<UserNotificationEntity> findByUserIdAndReadIsFalse(Long userId);

}
