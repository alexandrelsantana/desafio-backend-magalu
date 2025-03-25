package com.magalu.infrastructure.mappers;

import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.infrastructure.persistence.ScheduledMessageJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduledMessageMapper {

    @Mapping(source = "message", target = "message.text")
    @Mapping(source = "to", target = "message.to")
    @Mapping(source = "UUID", target = "uuid")
    ScheduledMessage toDomain(ScheduledMessageJpaEntity scheduledMessageJpaEntity);

    @Mapping(source = "message.text", target = "message")
    @Mapping(source = "message.to", target = "to")
    @Mapping(source = "uuid", target = "UUID")
    ScheduledMessageJpaEntity toJpaEntity(ScheduledMessage scheduledMessage);
}
