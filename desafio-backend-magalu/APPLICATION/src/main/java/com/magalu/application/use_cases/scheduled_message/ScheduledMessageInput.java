package com.magalu.application.use_cases.scheduled_message;

import java.time.LocalDateTime;

public record ScheduledMessageInput(
        LocalDateTime scheduledTime,
        String message,
        String to
    ){
    public static ScheduledMessageInput with(
            LocalDateTime scheduledTime,
            String message,
            String to) {

        return new ScheduledMessageInput (scheduledTime, message, to);
    }
}
