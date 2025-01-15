package com.dev.eventnotificator.notifications.api;

import com.dev.eventnotificator.notifications.EventChangeNotificationMapper;

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
    private final UserNotificationService userNotificationService;
    private final EventChangeNotificationMapper eventChangeNotificationMapper;

    public EventChangeNotificationController(
            UserUtil userUtil,
            UserNotificationService userNotificationService,
            EventChangeNotificationMapper eventChangeNotificationMapper
    ) {
        this.userUtil = userUtil;
        this.userNotificationService = userNotificationService;
        this.eventChangeNotificationMapper = eventChangeNotificationMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventChangeNotificationDTO>> getUnreadNotifications() {
        log.info("Received request to get unread notifications for the current user");
        Long userId = userUtil.getCurrentUserId();
        List<UserNotification> unreadNotifications = userNotificationService.getUnreadNotifications(userId);

        List<EventChangeNotificationDTO> eventChangeNotificationDTOS = unreadNotifications.stream()
                .map(UserNotification::eventChangeNotification)
                .map(eventChangeNotificationMapper::toDto)
                .toList();

        return ResponseEntity.ok(eventChangeNotificationDTOS);
    }
}
