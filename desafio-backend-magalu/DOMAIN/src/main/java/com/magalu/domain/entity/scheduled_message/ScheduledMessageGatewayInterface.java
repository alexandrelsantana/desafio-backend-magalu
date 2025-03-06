package com.magalu.domain.entity.scheduled_message;

public interface ScheduledMessageGatewayInterface {
    ScheduledMessage findById(String id);
    void save(ScheduledMessage scheduledMessage);
}
