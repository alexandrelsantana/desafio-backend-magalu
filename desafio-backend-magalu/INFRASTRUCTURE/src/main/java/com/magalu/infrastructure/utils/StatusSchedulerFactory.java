package com.magalu.infrastructure.utils;

import com.magalu.domain.entity.scheduled_message.status_scheduler.*;

import java.util.HashMap;
import java.util.Map;

public class StatusSchedulerFactory {
    private static final Map<String, StatusScheduler> STATUS_MAP = new HashMap<>();

    static {
        STATUS_MAP.put(StatusSchedulerCreated.CREATED, StatusSchedulerCreated.create());
        STATUS_MAP.put(StatusSchedulerCancelled.CANCELLED, StatusSchedulerCancelled.create());
        STATUS_MAP.put(StatusSchedulerScheduled.SCHEDULED, StatusSchedulerScheduled.create());
        STATUS_MAP.put(StatusSchedulerCompleted.COMPLETED, StatusSchedulerCompleted.create());
        STATUS_MAP.put(StatusSchedulerFailed.FAILED, StatusSchedulerFailed.create());
    }

    public static StatusScheduler getStatus(String status) {
        StatusScheduler statusScheduler = STATUS_MAP.get(status);

        if (statusScheduler == null) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        return statusScheduler;
    }
    
}
