package com.magalu.infrastructure.persistence;

import com.magalu.domain.entity.scheduled_message.status_scheduler.*;

public enum Status {

    CREATED(StatusSchedulerCreated.CREATED),
    COMPLETED(StatusSchedulerCompleted.COMPLETED),
    CANCELLED(StatusSchedulerCancelled.CANCELLED),
    SCHEDULED(StatusSchedulerScheduled.SCHEDULED),
    FAILED(StatusSchedulerFailed.FAILED);

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public static Status getStatus(String value) {
        for (Status status : Status.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status in database: " + value);
    }

    private String getValue() {
        return this.value;
    }
}
