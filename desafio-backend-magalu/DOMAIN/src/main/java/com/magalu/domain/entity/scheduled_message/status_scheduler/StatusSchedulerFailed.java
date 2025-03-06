package com.magalu.domain.entity.scheduled_message.status_scheduler;

public class StatusSchedulerFailed extends StatusScheduler {
    private static final String FAILED = "FAILED";

    private StatusSchedulerFailed(String status) {
        super(status);
    }

    public static StatusSchedulerFailed create(){
        return new StatusSchedulerFailed(FAILED);
    }

}
