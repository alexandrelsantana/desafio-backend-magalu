package com.magalu.infrastructure.scheduled_message;

import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.infrastructure.mappers.ScheduledMessageMapper;
import com.magalu.infrastructure.persistence.ScheduledMessageRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMessageGateway implements ScheduledMessageGatewayInterface {

    private final ScheduledMessageRepository scheduledMessageRepository;
    private final ScheduledMessageMapper scheduledMessageMapper;

    public ScheduledMessageGateway(
            ScheduledMessageRepository scheduledMessageRepository,
            ScheduledMessageMapper scheduledMessageMapper) {
        this.scheduledMessageRepository = scheduledMessageRepository;
        this.scheduledMessageMapper = scheduledMessageMapper;
    }

    @Override
    public ScheduledMessage findById(String id) {
        var scheduledMessage = this.scheduledMessageRepository.findById(id).map(scheduledMessageMapper::toDomain);
        return scheduledMessage.orElse(null);
    }

    @Override
    public void save(ScheduledMessage scheduledMessage) {
        this.scheduledMessageRepository.save(scheduledMessageMapper.toJpaEntity(scheduledMessage));
    }
}
