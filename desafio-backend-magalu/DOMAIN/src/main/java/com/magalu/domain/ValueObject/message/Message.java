package com.magalu.domain.ValueObject.message;

import com.magalu.domain.validation.Notification;
import lombok.Getter;

@Getter
public class Message {
    private final String text;
    private final String to;

    private Message(String text, String to) {
        this.text = text;
        this.to = to;
    }

    public static Message create(final String message, final String to, final Notification notification) {
        Message entity = new Message(message, to);
        entity.validate(notification);

        return entity;
    }

    public static Message create(final String message, final String to) {
        return new Message(message, to);
    }

    private void validate (Notification notification){
        new MessageValidator(notification).validate(this);
    }
}
