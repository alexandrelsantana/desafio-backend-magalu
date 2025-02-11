package com.magalu.domain.entity.scheduled_message.status_scheduler;

public class StatusSchedulerCompleted extends StatusScheduler {
    private static final String COMPLETED = "COMPLETED";

    private StatusSchedulerCompleted(String status) {
        super(status);
    }

    public static StatusSchedulerCompleted create(){
        return new StatusSchedulerCompleted(COMPLETED);
    }

}
