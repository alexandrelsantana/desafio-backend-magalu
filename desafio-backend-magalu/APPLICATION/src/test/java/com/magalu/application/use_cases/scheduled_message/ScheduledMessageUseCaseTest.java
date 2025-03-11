package com.magalu.application.use_cases.scheduled_message;

import com.magalu.application.use_cases.utils.output.StatusFailed;
import com.magalu.application.use_cases.utils.output.StatusSuccess;
import com.magalu.domain.ValueObject.message.MessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ScheduledMessageUseCaseTest {

    @Test
    @DisplayName("givenValidParameters_whenCallScheduledMessage_thenScheduledMessage")
    public void createScheduledMessageSuccess (){
        ScheduledMessageGatewayInterface scheduledMessageGateway = mock(ScheduledMessageGatewayInterface.class);
        MessageGatewayInterface messageGateway = mock(MessageGatewayInterface.class);
        ScheduledMessageUseCase useCase = new ScheduledMessageUseCase(scheduledMessageGateway, messageGateway);

        final LocalDateTime scheduledTime = LocalDateTime.now().plusSeconds(5);
        final String message = "Message test";
        final String to = "test@test.com";
        ScheduledMessageInput input = ScheduledMessageInput.with(
                scheduledTime,
                message,
                to
        );

        var output = useCase.execute(input);

        verify(scheduledMessageGateway, times(2)).save(any(ScheduledMessage.class));

        assertNotNull(output);
        assertInstanceOf(StatusSuccess.class, output.getStatusOutput());
    }

    @Test
    @DisplayName("givenInvalidParameters_whenNotCallScheduledMessage_thenReturnError")
    public void createScheduledMessageFailed (){
        ScheduledMessageGatewayInterface scheduledMessageGateway = mock(ScheduledMessageGatewayInterface.class);
        MessageGatewayInterface messageGateway = mock(MessageGatewayInterface.class);
        ScheduledMessageUseCase useCase = new ScheduledMessageUseCase(scheduledMessageGateway, messageGateway);

        final LocalDateTime scheduledTime = null;
        final String message = "Message test";
        final String to = "";
        ScheduledMessageInput input = ScheduledMessageInput.with(
                scheduledTime,
                message,
                to
        );

        var output = useCase.execute(input);

        assertNotNull(output);
        Assertions.assertInstanceOf(StatusFailed.class, output.getStatusOutput());
        Assertions.assertEquals("Field 'to' is empty", output.getNotification().getErrors().get(0).getDescription());
        Assertions.assertEquals("Field 'scheduledTime' is empty", output.getNotification().getErrors().get(1).getDescription());
        verify(scheduledMessageGateway, times(0)).save(any(ScheduledMessage.class));

    }

}