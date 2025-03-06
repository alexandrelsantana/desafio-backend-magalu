package com.magalu.infrastructure.configurations;

import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageUseCase;
import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageUseCaseAbstract;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelScheduledMessageUseCaseConfiguration {
    private final ScheduledMessageGatewayInterface scheduledMessageGateway;

    public CancelScheduledMessageUseCaseConfiguration(
            ScheduledMessageGatewayInterface scheduledMessageGateway) {
        this.scheduledMessageGateway = scheduledMessageGateway;
    }

    @Bean
    public CancelScheduledMessageUseCaseAbstract cancelScheduledMessageUseCase(){
        return new CancelScheduledMessageUseCase(scheduledMessageGateway);
    }


}
