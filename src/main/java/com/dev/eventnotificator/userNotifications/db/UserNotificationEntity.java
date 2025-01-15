package com.dev.eventnotificator.userNotifications.db;

import com.dev.eventnotificator.notifications.db.EventChangeNotificationEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_notifications")
public class UserNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "event_id", nullable = false)
    private EventChangeNotificationEntity eventChangeNotification;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_read", nullable = false)
    private boolean read;

    public UserNotificationEntity() {
    }

    public UserNotificationEntity(
            Long userId,
            EventChangeNotificationEntity eventChangeNotification,
            LocalDateTime createdAt,
            boolean read
    ) {
        this.userId = userId;
        this.eventChangeNotification = eventChangeNotification;
        this.createdAt = createdAt;
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

    public EventChangeNotificationEntity getEventId() {
        return eventChangeNotification;
    }

    public void setEventId(EventChangeNotificationEntity eventChangeNotification) {
        this.eventChangeNotification = eventChangeNotification;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public EventChangeNotificationEntity getEventChangeNotification() {
        return eventChangeNotification;
    }

    public void setEventChangeNotification(EventChangeNotificationEntity eventChangeNotification) {
        this.eventChangeNotification = eventChangeNotification;
    }
}
