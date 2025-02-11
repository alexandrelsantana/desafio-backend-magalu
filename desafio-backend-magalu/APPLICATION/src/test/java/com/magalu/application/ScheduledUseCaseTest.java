package com.magalu.application;

import com.magalu.application.use_cases.scheduled_message.ScheduledMessageInput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageOutput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCase;
import com.magalu.application.use_cases.utils.status_output.StatusFailed;
import com.magalu.application.use_cases.utils.status_output.StatusSuccess;
import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.ValueObject.message.MessageGateway;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledUseCaseTest {

    @InjectMocks
    private ScheduledMessageUseCase useCase;
    @Mock
    private MessageGateway messageGateway;
    @Mock
    private ScheduledMessageGateway scheduledMessageGateway;

    @Test
    @DisplayName("givenValidParameters_whenCallSchedulerUseCase_thenInstantiateUseCase")
    void execute() {

        final LocalDateTime scheduledTime = LocalDateTime.now().plusSeconds(5);
        final String MESSAGE = "Message";

        var input = ScheduledMessageInput.with(
                scheduledTime,
                MESSAGE,
                "teste@teste.com"
        );

        ScheduledMessageOutput output = useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertInstanceOf(StatusSuccess.class, output.getOutput());

        verify(scheduledMessageGateway, times(2)).save(any(ScheduledMessage.class));
        verify(messageGateway, never()).send(any(Message.class));

    }

    @Test
    @DisplayName("givenInvalidParameters_whenCallSchedulerUseCase_thenReturnOutputError")
    void execute2() {

        final LocalDateTime scheduledTime = null;
        final String MESSAGE = "Message";
        final String to = null;

        var input = ScheduledMessageInput.with(
                scheduledTime,
                MESSAGE,
                to
        );

        ScheduledMessageOutput output = useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertInstanceOf(StatusFailed.class, output.getOutput());

        Assertions.assertEquals(
                "Field 'to' is empty",
                output.getNotification().getErrors().get(0).getDescription());
        Assertions.assertEquals(
                "Field 'scheduledTime' is empty",
                output.getNotification().getErrors().get(1).getDescription());

    }
}