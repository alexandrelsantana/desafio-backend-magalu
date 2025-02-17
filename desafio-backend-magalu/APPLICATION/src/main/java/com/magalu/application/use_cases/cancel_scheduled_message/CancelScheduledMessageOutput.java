package com.magalu.application.use_cases.cancel_scheduled_message;

import com.magalu.application.use_cases.utils.output.Output;
import com.magalu.application.use_cases.utils.output.StatusOutput;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.validation.NotificationInterface;

public class CancelScheduledMessageOutput extends Output<ScheduledMessage> {

    private CancelScheduledMessageOutput(ScheduledMessage entity, StatusOutput statusOutput, NotificationInterface notification) {
        super(entity, statusOutput, notification);
    }

    public static CancelScheduledMessageOutput create(
            ScheduledMessage entity,
            StatusOutput statusOutput,
            NotificationInterface notification){
        return new CancelScheduledMessageOutput(entity, statusOutput, notification);
    }
}
