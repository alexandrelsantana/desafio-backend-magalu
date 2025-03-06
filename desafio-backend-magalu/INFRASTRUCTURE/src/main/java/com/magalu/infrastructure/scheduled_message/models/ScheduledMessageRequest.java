package com.magalu.infrastructure.scheduled_message.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.magalu.infrastructure.utils.serializers.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record ScheduledMessageRequest(
        @Schema(example = "Message")
        @JsonProperty("message") String message,

        @Schema(example = "teste@domain.com")
        @JsonProperty("to") String to,

        @Schema(example = "01/01/2999 00:00")
        @Future(message = "The scheduled date must be in the future")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonProperty("scheduled_time") LocalDateTime scheduledTime
) {
}

