package com.magalu.infrastructure.scheduled_message.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record RetrieveScheduledMessageRequest(

        @Schema(example = "70efeaf62baf45c1b28b9af8fe354223")
        @JsonProperty("uuid") String uuid
) {
}

