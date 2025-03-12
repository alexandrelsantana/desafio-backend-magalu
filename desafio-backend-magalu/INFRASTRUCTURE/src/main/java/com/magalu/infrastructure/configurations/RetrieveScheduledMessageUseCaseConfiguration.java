package com.magalu.infrastructure.configurations;

import com.magalu.application.use_cases.retrieve_scheduled.RetrieveScheduledMessageUseCase;
import com.magalu.application.use_cases.retrieve_scheduled.RetrieveScheduledMessageUseCaseAbstract;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrieveScheduledMessageUseCaseConfiguration {
    private final ScheduledMessageGatewayInterface scheduledMessageGateway;

    public RetrieveScheduledMessageUseCaseConfiguration(
            ScheduledMessageGatewayInterface scheduledMessageGateway) {
        this.scheduledMessageGateway = scheduledMessageGateway;
    }

    @Bean
    public RetrieveScheduledMessageUseCaseAbstract retrieveScheduledMessageUseCase(){
        return new RetrieveScheduledMessageUseCase(scheduledMessageGateway);
    }

}
