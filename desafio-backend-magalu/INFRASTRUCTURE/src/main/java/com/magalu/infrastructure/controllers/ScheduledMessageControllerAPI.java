package com.magalu.infrastructure.controllers;

import com.magalu.infrastructure.scheduled_message.models.ScheduledMessageRequest;
import com.magalu.infrastructure.scheduled_message.models.ScheduledMessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface ScheduledMessageControllerAPI {

    @Operation(
            summary = "Schedule a message",
            description = "This endpoint schedules a message to be sent at a specific time")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Message scheduled successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ScheduledMessageResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                    "isSuccess": true,
                                    "message": "Message scheduled",
                                    "data": {
                                        "uuid": "48b02c3d4c154806a7748a05ac72de3f",
                                        "scheduledTime": "2999-01-01T00:00:00",
                                        "message": {
                                            "message": "teste message",
                                            "to": "alexandre@gmail.com"
                                        },
                                        "statusScheduler": {
                                            "status": "SCHEDULED"
                                        }
                                    },
                                    "statusCode": 200,
                                    "timestamp": 1741272361672,
                                    "path": "/api/create-scheduler",
                                    "errors": []
                                }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Message not scheduled",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ScheduledMessageResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "isSuccess": false,
                                        "message": "Message not scheduled",
                                        "data": {
                                            "uuid": null,
                                            "scheduledTime": "2999-01-01T00:00:00",
                                            "message": {
                                                "message": null,
                                                "to": null
                                            },
                                            "statusScheduler": {
                                                "status": "FAILED"
                                            }
                                        },
                                        "statusCode": 400,
                                        "timestamp": 1741262374033,
                                        "path": "/api/create-scheduler",
                                        "errors": [
                                            {
                                                "description": "Field 'to' is empty",
                                                "message": "The 'to' field is required."
                                            },
                                            {
                                                "description": "Field 'message' is empty",
                                                "message": "The 'message' field is required."
                                            }
                                        ]
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<?> createScheduler(@Valid @RequestBody ScheduledMessageRequest request);

}