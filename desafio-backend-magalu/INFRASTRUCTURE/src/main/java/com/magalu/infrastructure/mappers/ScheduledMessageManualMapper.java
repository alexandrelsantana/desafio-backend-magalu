package com.magalu.infrastructure.mappers;

import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.infrastructure.persistence.ScheduledMessageJpaEntity;


/**
 * Mapper Manual. Deixei para exemplo
 */
public abstract class ScheduledMessageManualMapper {
    public static ScheduledMessageJpaEntity fromDomain(ScheduledMessage entityDomain){
        return new ScheduledMessageJpaEntity(
                entityDomain.getUuid(),
                entityDomain.getScheduledTime(),
                entityDomain.getStatusScheduler(),
                entityDomain.getMessageText(),
                entityDomain.getMessageTo()
        );
    }

    public static ScheduledMessage toDomain(ScheduledMessageJpaEntity entityJpa){
        return  ScheduledMessage.create(
                entityJpa.getUUID(),
                entityJpa.getScheduledTime(),
                Message.create(entityJpa.getMessage(), entityJpa.getTo()),
                entityJpa.getStatusScheduler()
        );
    }
}
