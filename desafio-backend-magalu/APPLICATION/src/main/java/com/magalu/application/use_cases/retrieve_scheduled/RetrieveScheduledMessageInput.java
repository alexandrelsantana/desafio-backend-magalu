package com.magalu.application.use_cases.retrieve_scheduled;

public record RetrieveScheduledMessageInput(
        String id
) {
    public static RetrieveScheduledMessageInput with(String id){
        return new RetrieveScheduledMessageInput(id);
    }
}
