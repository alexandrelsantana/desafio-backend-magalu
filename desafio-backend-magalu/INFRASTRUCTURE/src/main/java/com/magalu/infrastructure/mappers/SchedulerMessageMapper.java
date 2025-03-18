package com.magalu.infrastructure.mappers;

import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.infrastructure.persistence.ScheduledMessageJpaEntity;
import com.magalu.infrastructure.persistence.Status;
import com.magalu.infrastructure.utils.StatusSchedulerFactory;

public abstract class SchedulerMessageMapper {
    public static ScheduledMessageJpaEntity fromDomain(ScheduledMessage entityDomain){
        return new ScheduledMessageJpaEntity(
                entityDomain.getUuid(),
                entityDomain.getScheduledTime(),
                Status.getStatus(entityDomain.getStatusScheduler().getStatus()),
                entityDomain.getMessageText(),
                entityDomain.getMessageTo()
        );
    }

    public static ScheduledMessage toDomain(ScheduledMessageJpaEntity entityJpa){
        return  ScheduledMessage.create(
                entityJpa.getUUID(),
                entityJpa.getScheduledTime(),
                Message.create(entityJpa.getMessage(), entityJpa.getTo()),
                StatusSchedulerFactory.getStatus(entityJpa.getStatusScheduler().name())
        );
    }
}
