package com.dev.eventnotificator.notifications.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEventIdIn(List<Long> eventIds);

    void deleteAllByCreatedAtBefore(LocalDateTime createdAt);
}
