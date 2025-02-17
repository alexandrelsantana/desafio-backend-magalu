package com.magalu.application.use_cases.cancel_scheduled_message;

public record CancelScheduledMessageInput(
        String id
) {
    public static CancelScheduledMessageInput with(String id){
        return new CancelScheduledMessageInput(id);
    }
}
