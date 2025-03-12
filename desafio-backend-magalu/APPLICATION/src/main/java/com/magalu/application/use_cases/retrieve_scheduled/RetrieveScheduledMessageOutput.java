package com.magalu.application.use_cases.retrieve_scheduled;

import com.magalu.application.use_cases.utils.output.Output;
import com.magalu.application.use_cases.utils.output.StatusOutput;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.validation.NotificationInterface;

public class RetrieveScheduledMessageOutput extends Output<ScheduledMessage> {

    private RetrieveScheduledMessageOutput(ScheduledMessage entity, StatusOutput statusOutput, NotificationInterface notification) {
        super(entity, statusOutput, notification);
    }

    public static RetrieveScheduledMessageOutput create(
            ScheduledMessage entity,
            StatusOutput statusOutput,
            NotificationInterface notification){
        return new RetrieveScheduledMessageOutput(entity, statusOutput, notification);
    }
}
