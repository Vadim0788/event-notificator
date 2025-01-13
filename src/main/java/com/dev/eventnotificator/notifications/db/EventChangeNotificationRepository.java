package com.dev.eventnotificator.notifications.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventChangeNotificationRepository extends JpaRepository<EventChangeNotificationEntity, Long> {
    List<EventChangeNotificationEntity> findByEventIdIn(List<Long> eventIds);
}
