package com.magalu.domain.entity.scheduled_message.status_scheduler;

public class StatusSchedulerScheduled extends StatusScheduler {
    private static final String SCHEDULED = "SCHEDULED";

    private StatusSchedulerScheduled(String status) {
        super(status);
    }

    public static StatusSchedulerScheduled create(){
        return new StatusSchedulerScheduled(SCHEDULED);
    }

}
