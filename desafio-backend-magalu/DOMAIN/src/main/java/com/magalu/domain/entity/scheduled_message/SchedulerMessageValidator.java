package com.magalu.domain.entity.scheduled_message;

import com.magalu.domain.validation.Notification;
import com.magalu.domain.validation.Validator;

import java.time.LocalDateTime;

public class SchedulerMessageValidator extends Validator<ScheduledMessage> {

    protected SchedulerMessageValidator(
            final Notification notification) {
        super(notification);
    }

    @Override
    public void validate(ScheduledMessage entity) {
        final var scheduledTime = entity.getScheduledTime();
        final var message = entity.getMessage();

        if(message == null){
            notification.append(
                    "Field 'message' is null",
                    "The 'message' field is required."
            );
        }

        if(scheduledTime == null){
            notification.append(
                    "Field 'scheduledTime' is empty",
                    "The 'scheduledTime' field is required."
            );
            return;
        }

        if(scheduledTime.isBefore(LocalDateTime.now())){
            notification.append(
                    "Field is not in the future",
                    "The 'scheduledTime' must be in the future"
            );
        }
    }
}
