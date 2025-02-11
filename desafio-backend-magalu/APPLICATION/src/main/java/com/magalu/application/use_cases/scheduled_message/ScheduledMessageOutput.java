package com.magalu.application.use_cases.scheduled_message;

import com.magalu.application.use_cases.utils.status_output.StatusOutput;
import com.magalu.application.use_cases.Output;
import com.magalu.domain.validation.NotificationInterface;

public class ScheduledMessageOutput extends Output<StatusOutput> {

    private ScheduledMessageOutput(StatusOutput output, NotificationInterface notification) {
        super(output, notification);
    }

    public static  ScheduledMessageOutput createScheduledMessageOutput(
            StatusOutput output,
            NotificationInterface notification){
        return new ScheduledMessageOutput(output, notification);
    }

}
