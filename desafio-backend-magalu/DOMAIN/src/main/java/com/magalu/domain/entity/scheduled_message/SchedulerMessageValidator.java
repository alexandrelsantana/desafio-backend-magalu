package com.magalu.domain.entity.scheduled_message;

import com.magalu.domain.validation.Notification;
import com.magalu.domain.validation.Validator;

public class SchedulerMessageValidator extends Validator<ScheduledMessage> {

    protected SchedulerMessageValidator(
            final Notification notification) {
        super(notification);
    }

    @Override
    public void validate(ScheduledMessage entity) {
        final String to = entity.getMessage().getTo();
        if(to == null || to.isEmpty()){
            notification.append(
                    "Field 'to' is empty",
                    "The 'to' field is required."
            );
        }

        if(entity.getScheduledTime() == null){
            notification.append(
                    "Field 'scheduledTime' is empty",
                    "The 'scheduledTime' field is required."
            );
        }

    }
}
