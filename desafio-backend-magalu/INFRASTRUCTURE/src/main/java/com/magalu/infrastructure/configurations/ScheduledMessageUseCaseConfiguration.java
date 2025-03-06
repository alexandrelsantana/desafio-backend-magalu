package com.magalu.infrastructure.configurations;

import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCase;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCaseAbstract;
import com.magalu.domain.ValueObject.message.MessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledMessageUseCaseConfiguration {
    private final ScheduledMessageGatewayInterface scheduledMessageGateway;
    private final MessageGatewayInterface messageGateway;

    public ScheduledMessageUseCaseConfiguration(
            ScheduledMessageGatewayInterface scheduledMessageGateway,
            MessageGatewayInterface messageGateway) {
        this.scheduledMessageGateway = scheduledMessageGateway;
        this.messageGateway = messageGateway;
    }

    @Bean
    public ScheduledMessageUseCaseAbstract scheduledMessageUseCase(){
        return new ScheduledMessageUseCase(scheduledMessageGateway, messageGateway);
    }


}
