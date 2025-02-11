package com.magalu.application.use_cases.utils.scheduler;

import com.magalu.application.use_cases.scheduled_message.CreateScheduledMessageTask;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGateway;
import com.magalu.domain.validation.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CreateScheduledMessageTaskTest {

    @Mock
    ScheduledMessageGateway scheduledMessageGateway;

    @Test
    @DisplayName("givenValidParameters_whenCallCreateMessageTask_thenScheduledMessage")
    public void execute_success() throws ExecutionException, InterruptedException {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime targetTime = now.plusSeconds(3);
        final String MESSAGE = "Message Test " + LocalDateTime.now();
        final String to = "test@123,com";
        final Runnable task = () -> System.out.println(MESSAGE);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStreamCaptor));

        Notification notification = Notification.createNotification();

        final var scheduledMessage = ScheduledMessage.create(
                targetTime,
                MESSAGE,
                to,
                notification
        );

        Assertions.assertFalse(notification.hasError());

        CreateScheduledMessageTask createScheduledMessageTask = new CreateScheduledMessageTask(scheduledMessageGateway);
        ScheduledFuture<?> future = createScheduledMessageTask.execute(scheduledMessage, task);

        ZonedDateTime nowZoned = now.atZone(ZoneId.systemDefault());
        ZonedDateTime targetZoned = targetTime.atZone(ZoneId.systemDefault());
        long expectedDelay = targetZoned.toInstant().toEpochMilli() - nowZoned.toInstant().toEpochMilli();

        long actualDelay = future.getDelay(TimeUnit.MILLISECONDS);

        assertTrue(Math.abs(expectedDelay - actualDelay) < 1000, "Delay calculado está fora do esperado");

        future.get();

        String printedOutput = outputStreamCaptor.toString().trim();
        assertEquals(printedOutput, MESSAGE, "A mensagem impressa não é igual à esperada.");
        System.setOut(originalOut);
    }

}