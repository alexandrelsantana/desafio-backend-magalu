package com.magalu.application.use_cases.cancel_scheduled_message;

import com.magalu.application.use_cases.utils.output.StatusFailed;
import com.magalu.application.use_cases.utils.output.StatusSuccess;
import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerCancelled;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerCompleted;
import com.magalu.domain.entity.scheduled_message.status_scheduler.StatusSchedulerScheduled;
import com.magalu.domain.validation.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CancelScheduledMessageUseCaseTest {
    @Test
    @DisplayName("givenValidParameters_whenCancelScheduledMessage_cancelSendMessage")
    public void executeSuccess(){
        ScheduledMessageGatewayInterface scheduledMessageGateway = mock(ScheduledMessageGatewayInterface.class);
        final LocalDateTime scheduledTime = LocalDateTime.now().plusSeconds(5);
        final String message = "Message test";
        final String to = "test@test.com";
        final Notification notification = Notification.createNotification();
        var schedulerMessage = ScheduledMessage.create(
                scheduledTime,
                Message.create(message, to, notification),
                notification
        );
        schedulerMessage.changeStatus(StatusSchedulerScheduled.create());
        final String id = schedulerMessage.getUuid();

        when(scheduledMessageGateway.findById(id)).thenReturn(schedulerMessage);

        CancelScheduledMessageUseCase useCase = new CancelScheduledMessageUseCase(scheduledMessageGateway);

        var input = CancelScheduledMessageInput.with(id);
        var output = useCase.execute(input);

        assertNotNull(output);
        Assertions.assertInstanceOf(StatusSuccess.class, output.getStatusOutput());
        verify(scheduledMessageGateway, times(1)).save(any(ScheduledMessage.class));
        verify(scheduledMessageGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("givenIvValidParameters_whenCancelScheduledMessage_thenDoNotCancelSendMessage")
    public void executeFailed(){
        ScheduledMessageGatewayInterface scheduledMessageGateway = mock(ScheduledMessageGatewayInterface.class);
        final LocalDateTime scheduledTime = LocalDateTime.now().plusSeconds(5);
        final String message = "Message test";
        final String to = "test@test.com";
        final Notification notification = Notification.createNotification();
        var schedulerMessage = ScheduledMessage.create(
                scheduledTime,
                Message.create(message, to, notification),
                notification
        );
        schedulerMessage.changeStatus(StatusSchedulerCancelled.create());
        final String id = schedulerMessage.getUuid();

        when(scheduledMessageGateway.findById(id)).thenReturn(schedulerMessage);

        CancelScheduledMessageUseCase useCase = new CancelScheduledMessageUseCase(scheduledMessageGateway);

        var input = CancelScheduledMessageInput.with(id);
        var output = useCase.execute(input);

        assertNotNull(output);
        Assertions.assertInstanceOf(StatusFailed.class, output.getStatusOutput());
        verify(scheduledMessageGateway, times(0)).save(any(ScheduledMessage.class));
        verify(scheduledMessageGateway, times(1)).findById(id);
        Assertions.assertEquals("Error when cancel scheduled", output.getNotification().getErrors().get(0).getDescription());
        Assertions.assertEquals("Scheduled message is already cancelled", output.getNotification().getErrors().get(0).getMessage());
    }

    @Test
    @DisplayName("givenIvValidParameters_whenCancelScheduledMessage_thenDoNotCancelSendMessage")
    public void executeFailed2(){
        ScheduledMessageGatewayInterface scheduledMessageGateway = mock(ScheduledMessageGatewayInterface.class);
        final LocalDateTime scheduledTime = LocalDateTime.now().plusSeconds(5);
        final String message = "Message test";
        final String to = "test@test.com";
        final Notification notification = Notification.createNotification();
        var schedulerMessage = ScheduledMessage.create(
                scheduledTime,
                Message.create(message, to, notification),
                notification
        );
        schedulerMessage.changeStatus(StatusSchedulerCompleted.create());
        final String id = schedulerMessage.getUuid();

        when(scheduledMessageGateway.findById(id)).thenReturn(schedulerMessage);

        CancelScheduledMessageUseCase useCase = new CancelScheduledMessageUseCase(scheduledMessageGateway);

        var input = CancelScheduledMessageInput.with(id);
        var output = useCase.execute(input);

        assertNotNull(output);
        Assertions.assertInstanceOf(StatusFailed.class, output.getStatusOutput());
        verify(scheduledMessageGateway, times(0)).save(any(ScheduledMessage.class));
        verify(scheduledMessageGateway, times(1)).findById(id);
        Assertions.assertEquals("Error when cancel scheduled", output.getNotification().getErrors().get(0).getDescription());
        Assertions.assertEquals("The scheduled message cannot be canceled as it is not scheduled", output.getNotification().getErrors().get(0).getMessage());
    }

}