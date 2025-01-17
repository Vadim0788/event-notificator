package com.dev.eventnotificator.notifications;

public record FieldChange<T>(
        T oldField,
        T newField
) {
}