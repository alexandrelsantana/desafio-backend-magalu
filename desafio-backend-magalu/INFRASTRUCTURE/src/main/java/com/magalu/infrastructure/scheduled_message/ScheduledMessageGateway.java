package com.magalu.infrastructure.scheduled_message;

import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMessageGateway implements ScheduledMessageGatewayInterface {
    @Override
    public ScheduledMessage findById(String id) {
        return null;
    }

    @Override
    public void save(ScheduledMessage scheduledMessage) {

    }
}
