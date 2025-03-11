package com.magalu.domain.entity.scheduled_message.status_scheduler;

public class StatusSchedulerCancelled extends StatusScheduler {
    public static final String CANCELLED = "CANCELLED";

    private StatusSchedulerCancelled(String status) {
        super(status);
    }

    public static StatusSchedulerCancelled create(){
        return new StatusSchedulerCancelled(CANCELLED);
    }

}
