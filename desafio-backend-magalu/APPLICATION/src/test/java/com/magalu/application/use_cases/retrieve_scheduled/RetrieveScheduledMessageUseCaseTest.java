package com.magalu.application.use_cases.retrieve_scheduled;

import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageInput;
import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageUseCase;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageInput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCase;
import com.magalu.application.use_cases.utils.output.StatusFailed;
import com.magalu.application.use_cases.utils.output.StatusSuccess;
import com.magalu.domain.ValueObject.message.MessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerScheduled;
import com.magalu.domain.validation.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveScheduledMessageUseCaseTest {

    @InjectMocks
    private RetrieveScheduledMessageUseCase useCase;
    @Mock
    private MessageGatewayInterface messageGateway;
    @Mock
    private ScheduledMessageGatewayInterface scheduledMessageGateway;

    @Test
    @DisplayName("givenValidParameters_whenCallScheduledMessage_thenReturnScheduledMessage")
    public void RetrieveScheduledMessageSuccess (){

        final LocalDateTime scheduledTime = LocalDateTime.now().plusSeconds(5);
        final String message = "Message test";
        final String to = "test@test.com";
        final Notification notification = Notification.createNotification();
        var schedulerMessage = ScheduledMessage.create(
                scheduledTime,
                message,
                to,
                notification
        );
        schedulerMessage.changeStatus(StatusSchedulerScheduled.create());
        final String id = schedulerMessage.getUuid();

        when(scheduledMessageGateway.findById(id)).thenReturn(schedulerMessage);

        useCase = new RetrieveScheduledMessageUseCase(scheduledMessageGateway);
        var input = RetrieveScheduledMessageInput.with(id);
        var output = useCase.execute(input);

        assertNotNull(output);
        Assertions.assertInstanceOf(StatusSuccess.class, output.getStatusOutput());
        verify(scheduledMessageGateway, times(0)).save(any(ScheduledMessage.class));
        verify(scheduledMessageGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("givenInvalidParameters_whenCallScheduledMessage_thenReturnError")
    public void RetrieveScheduledMessageFailed (){

        final String id = null;

        useCase = new RetrieveScheduledMessageUseCase(scheduledMessageGateway);
        var input = RetrieveScheduledMessageInput.with(id);
        var output = useCase.execute(input);

        assertNotNull(output);
        Assertions.assertInstanceOf(StatusFailed.class, output.getStatusOutput());
        verify(scheduledMessageGateway, times(0)).save(any(ScheduledMessage.class));
        verify(scheduledMessageGateway, times(0)).findById(id);

        Assertions.assertEquals("Error when validate uuid", output.getNotification().getErrors().get(0).getDescription());


    }

}