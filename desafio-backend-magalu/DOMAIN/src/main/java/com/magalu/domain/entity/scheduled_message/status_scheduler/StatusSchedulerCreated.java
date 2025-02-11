package com.magalu.domain.entity.scheduled_message.status_scheduler;

public class StatusSchedulerCreated extends StatusScheduler {
    private static final String CREATED = "CREATED";

    private StatusSchedulerCreated(String status) {
        super(status);
    }

    public static StatusSchedulerCreated create(){
        return new StatusSchedulerCreated(CREATED);
    }

}
