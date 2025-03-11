package com.magalu.infrastructure.scheduled_message;

import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.infrastructure.persistence.ScheduledMessageJpaEntity;
import com.magalu.infrastructure.persistence.ScheduledMessageRepository;
import com.magalu.infrastructure.mappers.SchedulerMessageMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ScheduledMessageGateway implements ScheduledMessageGatewayInterface {

    private final ScheduledMessageRepository scheduledMessageRepository;

    public ScheduledMessageGateway(ScheduledMessageRepository scheduledMessageRepository) {
        this.scheduledMessageRepository = scheduledMessageRepository;
    }

    @Override
    public ScheduledMessage findById(String id) {
        Optional<ScheduledMessageJpaEntity> byId = this.scheduledMessageRepository.findById(id);

        if (byId.isEmpty()){
            return null;
        }

        return SchedulerMessageMapper.toDomain(byId.get());
    }

    @Override
    public void save(ScheduledMessage scheduledMessage) {
        this.scheduledMessageRepository.save(SchedulerMessageMapper.fromDomain(scheduledMessage));
    }
}
