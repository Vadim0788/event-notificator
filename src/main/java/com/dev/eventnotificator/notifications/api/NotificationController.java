package com.dev.eventnotificator.notifications.api;

import com.dev.eventnotificator.notifications.NotificationMapper;

import com.dev.eventnotificator.security.user.UserUtil;
import com.dev.eventnotificator.userNotifications.domain.UserNotification;
import com.dev.eventnotificator.userNotifications.domain.UserNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final UserUtil userUtil;
    private final UserNotificationService userNotificationService;
    private final NotificationMapper notificationMapper;

    public NotificationController(
            UserUtil userUtil,
            UserNotificationService userNotificationService,
            NotificationMapper notificationMapper
    ) {
        this.userUtil = userUtil;
        this.userNotificationService = userNotificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        log.info("Received request to get unread notifications for the current user");
        Long userId = userUtil.getCurrentUserId();
        List<UserNotification> unreadNotifications = userNotificationService.getUnreadNotifications(userId);

        List<NotificationDTO> notificationDTOS = unreadNotifications.stream()
                .map(UserNotification::notification)
                .map(notificationMapper::toDto)
                .toList();

        return ResponseEntity.ok(notificationDTOS);
    }
}
