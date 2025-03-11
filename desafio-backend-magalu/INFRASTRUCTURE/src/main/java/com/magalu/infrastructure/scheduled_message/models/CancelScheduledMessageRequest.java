package com.magalu.infrastructure.scheduled_message.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.magalu.infrastructure.utils.serializers.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record CancelScheduledMessageRequest(

        @Schema(example = "70efeaf62baf45c1b28b9af8fe354223")
        @JsonProperty("uuid") String uuid
) {
}

