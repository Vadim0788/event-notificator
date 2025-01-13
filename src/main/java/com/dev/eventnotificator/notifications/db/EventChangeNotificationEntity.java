package com.dev.eventnotificator.notifications.db;

import com.dev.eventnotificator.notifications.FieldChange;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "event_change_notification")
public class EventChangeNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;


    @Column(name = "changed_by_id", nullable = false)
    private Long changedById;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldField", column = @Column(name = "name_old")),
            @AttributeOverride(name = "newField", column = @Column(name = "name_new"))
    })
    private FieldChange<String> name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldField", column = @Column(name = "max_places_old")),
            @AttributeOverride(name = "newField", column = @Column(name = "max_places_new"))
    })
    private FieldChange<Long> maxPlaces;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldField", column = @Column(name = "date_old")),
            @AttributeOverride(name = "newField", column = @Column(name = "date_new"))
    })
    private FieldChange<OffsetDateTime> date;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldField", column = @Column(name = "cost_old")),
            @AttributeOverride(name = "newField", column = @Column(name = "cost_new"))
    })
    private FieldChange<BigDecimal> cost;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldField", column = @Column(name = "duration_old")),
            @AttributeOverride(name = "newField", column = @Column(name = "duration_new"))
    })
    private FieldChange<Integer> duration;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oldField", column = @Column(name = "location_id_old")),
            @AttributeOverride(name = "newField", column = @Column(name = "location_id_new"))
    })
    private FieldChange<Long> locationId;

    @ElementCollection
    @CollectionTable(name = "event_subscribers", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "subscriber_id")
    private List<Long> subscribersId;

    public EventChangeNotificationEntity() {
    }

    public EventChangeNotificationEntity(
            Long id,
            Long eventId,
            FieldChange<String> name,
            FieldChange<Long> maxPlaces,
            FieldChange<OffsetDateTime> date,
            FieldChange<BigDecimal> cost,
            FieldChange<Integer> duration,
            FieldChange<Long> locationId,
            List<Long> subscribersId
    ) {
        this.id = id;
        this.eventId = eventId;
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.date = date;
        this.cost = cost;
        this.duration = duration;
        this.locationId = locationId;
        this.subscribersId = subscribersId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getChangedById() {
        return changedById;
    }

    public void setChangedById(Long changedById) {
        this.changedById = changedById;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public FieldChange<String> getName() {
        return name;
    }

    public void setName(FieldChange<String> name) {
        this.name = name;
    }

    public FieldChange<Long> getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(FieldChange<Long> maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public FieldChange<OffsetDateTime> getDate() {
        return date;
    }

    public void setDate(FieldChange<OffsetDateTime> date) {
        this.date = date;
    }

    public FieldChange<BigDecimal> getCost() {
        return cost;
    }

    public void setCost(FieldChange<BigDecimal> cost) {
        this.cost = cost;
    }

    public FieldChange<Integer> getDuration() {
        return duration;
    }

    public void setDuration(FieldChange<Integer> duration) {
        this.duration = duration;
    }

    public FieldChange<Long> getLocationId() {
        return locationId;
    }

    public void setLocationId(FieldChange<Long> locationId) {
        this.locationId = locationId;
    }

    public List<Long> getSubscribersId() {
        return subscribersId;
    }

    public void setSubscribersId(List<Long> subscribersId) {
        this.subscribersId = subscribersId;
    }
}