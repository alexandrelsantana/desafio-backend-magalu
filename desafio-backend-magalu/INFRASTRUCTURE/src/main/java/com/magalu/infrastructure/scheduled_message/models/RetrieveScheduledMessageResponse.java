package com.magalu.infrastructure.scheduled_message.models;

import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.validation.Error;
import com.magalu.infrastructure.utils.ApiResponse;

import java.util.List;

public class RetrieveScheduledMessageResponse extends ApiResponse<ScheduledMessage> {
    private RetrieveScheduledMessageResponse(
            final boolean isSuccess,
            final String message,
            final ScheduledMessage data,
            final List<Error> erros,
            final int statusCode,
            final long timestamp,
            final String path
    ) {
        super(isSuccess, message, data, erros, statusCode, timestamp, path);
    }
}
