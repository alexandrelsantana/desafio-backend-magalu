package com.magalu.domain.entity.scheduled_message;

import com.magalu.domain.validation.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ScheduledMessageTest {

    @Test
    @DisplayName("givenValidParameters_whenCallNewEntity_thenInstantiateObject")
    void createSuccess() {
        final LocalDateTime scheduledTime = LocalDateTime.now().plusSeconds(5);
        final String message = "Message";
        final String to = "test@test.com";
        final String status = "CREATED";

        Notification notification = Notification.createNotification();

        var entity = ScheduledMessage.create(
                scheduledTime,
                message,
                to,
                notification
        );

        Assertions.assertFalse(notification.hasError());

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(scheduledTime, entity.getScheduledTime());
        Assertions.assertEquals(message, entity.getMessage().getMessage());
        Assertions.assertEquals(to, entity.getMessage().getTo());
        Assertions.assertEquals(status, entity.getStatusScheduler().getStatus());

        new SchedulerMessageValidator(notification).validate(entity);
        Assertions.assertFalse(notification.hasError());
    }

    @Test
    @DisplayName("givenInvalidScheduledTimeFormat_whenValidateEntity_thenNotificationError")
    void createError() {
        final LocalDateTime scheduledTime = null;
        final String message = "Message";
        final String to = null;

        Notification notification = Notification.createNotification();

        var entity = ScheduledMessage.create(
                scheduledTime,
                message,
                to,
                notification
        );

        Assertions.assertTrue(notification.hasError());
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(message, entity.getMessage().getMessage());

        Assertions.assertEquals(
                "Field 'scheduledTime' is empty",
                notification.getErrors().get(1).getDescription());
        Assertions.assertEquals(
                "Field 'to' is empty",
                notification.getErrors().get(0).getDescription());
    }


}