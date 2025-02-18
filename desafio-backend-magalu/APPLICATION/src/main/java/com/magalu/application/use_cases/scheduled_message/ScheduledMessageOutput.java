package com.magalu.application.use_cases.scheduled_message;

import com.magalu.application.use_cases.utils.output.StatusOutput;
import com.magalu.application.use_cases.utils.output.Output;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.validation.NotificationInterface;

public class ScheduledMessageOutput extends Output<ScheduledMessage> {

    private ScheduledMessageOutput(ScheduledMessage entity, StatusOutput output, NotificationInterface notification) {
        super(entity, output, notification);
    }

    public static  ScheduledMessageOutput create(
            ScheduledMessage entity,
            StatusOutput output,
            NotificationInterface notification){
        return new ScheduledMessageOutput(entity, output, notification);
    }

}
