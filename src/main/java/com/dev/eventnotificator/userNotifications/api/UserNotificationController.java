package com.dev.eventnotificator.userNotifications.api;

import com.dev.eventnotificator.security.user.UserUtil;
import com.dev.eventnotificator.userNotifications.domain.UserNotificationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/notifications")
public class UserNotificationController {
    private static final Logger log = LoggerFactory.getLogger(UserNotificationController.class);
    private final UserNotificationService userNotificationService;
    private final UserUtil userUtil;

    public UserNotificationController(
            UserNotificationService userNotificationService, UserUtil userUtil
    ) {
        this.userNotificationService = userNotificationService;
        this.userUtil = userUtil;
    }

    @PostMapping
    public ResponseEntity<String> getAllUserEvents(
            @RequestBody @Valid NotificationIdsRequest notificationIdsRequest
    ) {
        log.info("Received POST request to mark notifications as read");
        Long usrId = userUtil.getCurrentUserId();
        userNotificationService.markNotificationsAsRead(usrId, notificationIdsRequest.notificationIds());

        String answer = "Notifications marked as read successfully";
        return ResponseEntity.ok(answer);
    }
}
