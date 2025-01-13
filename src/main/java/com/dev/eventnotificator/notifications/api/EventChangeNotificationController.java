package com.dev.eventnotificator.notifications.api;

import com.dev.eventnotificator.notifications.domain.EventChangeNotificationService;

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
public class EventChangeNotificationController {
    private static final Logger log = LoggerFactory.getLogger(EventChangeNotificationController.class);
    private final UserUtil userUtil;
    private final EventChangeNotificationService notificationService;
    private final UserNotificationService userNotificationService;

    public EventChangeNotificationController(
            UserUtil userUtil,
            EventChangeNotificationService notificationService,
            UserNotificationService userNotificationService
    ) {
        this.userUtil = userUtil;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
    }

    @GetMapping
    public ResponseEntity<List<EventChangeNotificationDTO>> getUnreadNotifications() {
        log.info("Received request to get unread notifications for the current user");
        Long userId = userUtil.getCurrentUserId();
        List<UserNotification> unreadNotifications = userNotificationService.getUnreadNotifications(userId);
        List<Long> eventIds = unreadNotifications.stream()
                .map(UserNotification::eventId)
                .toList();

        List<EventChangeNotificationDTO> eventChangeNotificationDTOS =
                notificationService.getEventChangeNotificationsByIds(eventIds);

        return ResponseEntity.ok(eventChangeNotificationDTOS);
    }
}
