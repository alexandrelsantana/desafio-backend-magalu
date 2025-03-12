package com.magalu.infrastructure.scheduled_message;

import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.infrastructure.mappers.SchedulerMessageMapper;
import com.magalu.infrastructure.persistence.ScheduledMessageRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMessageGateway implements ScheduledMessageGatewayInterface {

    private final ScheduledMessageRepository scheduledMessageRepository;

    public ScheduledMessageGateway(ScheduledMessageRepository scheduledMessageRepository) {
        this.scheduledMessageRepository = scheduledMessageRepository;
    }

    @Override
    public ScheduledMessage findById(String id) {
        var scheduledMessage = this.scheduledMessageRepository.findById(id).map(SchedulerMessageMapper::toDomain);
        return scheduledMessage.orElse(null);
    }

    @Override
    public void save(ScheduledMessage scheduledMessage) {
        this.scheduledMessageRepository.save(SchedulerMessageMapper.fromDomain(scheduledMessage));
    }
}
