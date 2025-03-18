package com.magalu.domain.ValueObject.message;

import com.magalu.domain.validation.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    @DisplayName("givenValidParameters_whenCallNewEntity_thenInstantiateObject")
    void createSuccess() {
        final String message = "Message";
        final String to = "test@test.com";

        Notification notification = Notification.createNotification();

        var entity = Message.create(
                message,
                to,
                notification
        );

        Assertions.assertFalse(notification.hasError());

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(message, entity.getText());
        Assertions.assertEquals(to, entity.getTo());

        Assertions.assertFalse(notification.hasError());
    }

    @Test
    @DisplayName("givenInvalidScheduledTimeFormat_whenValidateEntity_thenNotificationError")
    void createError() {
        final String message = "Message";
        final String to = null;

        Notification notification = Notification.createNotification();

        var entity = Message.create(
                message,
                to,
                notification
        );

        Assertions.assertTrue(notification.hasError());
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(message, entity.getText());

        Assertions.assertEquals(
                "Field 'message' is empty",
                notification.getErrors().get(1).getDescription());
        Assertions.assertEquals(
                "Field 'to' is empty",
                notification.getErrors().get(0).getDescription());
    }


}