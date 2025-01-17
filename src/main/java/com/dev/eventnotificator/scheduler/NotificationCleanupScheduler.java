package com.dev.eventnotificator.scheduler;

import com.dev.eventnotificator.userNotifications.domain.UserNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationCleanupScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationCleanupScheduler.class);
    private final UserNotificationService userNotificationService;

    public NotificationCleanupScheduler(UserNotificationService userNotificationService) {
        this.userNotificationService = userNotificationService;
    }

    @Scheduled(cron = "${scheduler.cleanup.cron}")
    public void cleanOldNotifications() {
        log.info("Starting cleanup of old notifications...");
         userNotificationService.deleteOldNotificationsAndEvents(7);

    }
}
