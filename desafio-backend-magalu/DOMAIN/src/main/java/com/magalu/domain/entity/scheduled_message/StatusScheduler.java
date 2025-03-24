package com.magalu.domain.entity.scheduled_message;

public enum StatusScheduler {
    CREATED("CREATED"),
    SCHEDULED("SCHEDULED"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED"),
    CANCELLED("CANCELLED");

    private final String status;

    StatusScheduler(String status) {
        this.status = status;
    }
}
