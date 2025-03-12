package com.magalu.application.use_cases.retrieve_scheduled;

import com.magalu.domain.validation.Notification;
import com.magalu.domain.validation.Validator;

public class RetrieveScheduledMessageInputValidator extends Validator<RetrieveScheduledMessageInput> {
    protected RetrieveScheduledMessageInputValidator(Notification notification) {
        super(notification);
    }

    @Override
    public void validate(RetrieveScheduledMessageInput input) {
        if (input.id() == null || input.id().isBlank()){
            notification.append(
                    "Error when validate uuid",
                    "uuid cannot be null or empty"
            );
            return;
        }

        if (!input.id().matches(".*\\d.*")){
            notification.append(
                    "Error when validate uuid",
                    "uui must contain numbers"
            );
        }
    }
}
