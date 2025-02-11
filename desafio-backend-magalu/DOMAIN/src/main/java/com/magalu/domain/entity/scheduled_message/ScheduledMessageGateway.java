package com.magalu.domain.entity.scheduled_message;

public interface ScheduledMessageGateway {
    ScheduledMessage findById(String id);
    void save(ScheduledMessage scheduledMessage);
}
