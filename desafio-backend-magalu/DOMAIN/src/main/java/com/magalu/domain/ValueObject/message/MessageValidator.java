package com.magalu.domain.ValueObject.message;

import com.magalu.domain.validation.Notification;
import com.magalu.domain.validation.Validator;

public class MessageValidator extends Validator<Message> {

    public MessageValidator(Notification notification) {
        super(notification);
    }

    @Override
    public void validate(Message entity) {
        final String to = entity.getTo();
        final String message = entity.getTo();

        if(to == null || to.isEmpty()){
            notification.append(
                    "Field 'to' is empty",
                    "The 'to' field is required."
            );
        }

        if(message == null || message.isEmpty()){
            notification.append(
                    "Field 'message' is empty",
                    "The 'message' field is required."
            );
        }
    }
}
