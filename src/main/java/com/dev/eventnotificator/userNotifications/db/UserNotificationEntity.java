package com.dev.eventnotificator.userNotifications.db;

import com.dev.eventnotificator.notifications.db.Notification;
import jakarta.persistence.*;

@Entity
@Table(name = "user_notifications")
public class UserNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "event_change_notifiction_id", nullable = false)
    private Notification eventChangeNotification;

    @Column(name = "is_read", nullable = false)
    private boolean read;

    public UserNotificationEntity() {
    }

    public UserNotificationEntity(
            Long userId,
            Notification eventChangeNotification,
            boolean read
    ) {
        this.userId = userId;
        this.eventChangeNotification = eventChangeNotification;

        this.read = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Notification getEventId() {
        return eventChangeNotification;
    }

    public void setEventId(Notification eventChangeNotification) {
        this.eventChangeNotification = eventChangeNotification;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Notification getEventChangeNotification() {
        return eventChangeNotification;
    }

    public void setEventChangeNotification(Notification eventChangeNotification) {
        this.eventChangeNotification = eventChangeNotification;
    }
}
