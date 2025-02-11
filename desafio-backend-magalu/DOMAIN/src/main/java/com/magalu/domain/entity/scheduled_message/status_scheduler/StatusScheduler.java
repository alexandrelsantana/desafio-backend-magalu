package com.magalu.domain.entity.scheduled_message.status_scheduler;

import lombok.Getter;

@Getter
public abstract class StatusScheduler {
    private final String status;

    public StatusScheduler(String status){
        this.status = status;
    }
}
