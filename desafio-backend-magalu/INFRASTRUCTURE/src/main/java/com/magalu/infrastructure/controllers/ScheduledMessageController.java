package com.magalu.infrastructure.controllers;

import com.google.gson.Gson;
import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageUseCaseAbstract;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageInput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageOutput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCaseAbstract;
import com.magalu.infrastructure.scheduled_message.models.ScheduledMessageRequest;
import com.magalu.infrastructure.scheduled_message.models.ScheduledMessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Validated
@RequestMapping("/api")
public class ScheduledMessageController implements ScheduledMessageControllerAPI{

    private final Gson gson = new Gson();

    final ScheduledMessageUseCaseAbstract scheduledMessageUseCase;
    final CancelScheduledMessageUseCaseAbstract cancelScheduledMessageUseCase;

    public ScheduledMessageController(
            ScheduledMessageUseCaseAbstract scheduledMessageUseCase,
            CancelScheduledMessageUseCaseAbstract cancelScheduledMessageUseCase) {
        this.scheduledMessageUseCase = scheduledMessageUseCase;
        this.cancelScheduledMessageUseCase = cancelScheduledMessageUseCase;
    }

    @PostMapping("/create-scheduler")
    @Override
    public ResponseEntity<?> createScheduler(@Valid ScheduledMessageRequest request
    ){
        var thisRoute = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        var input = new ScheduledMessageInput(
                request.scheduledTime(),
                request.message(),
                request.to()
        );
        ScheduledMessageOutput output = scheduledMessageUseCase.execute(input);

        if (output.hasError()){
            var response = ScheduledMessageResponse.createFailed(
                    "Message not scheduled",
                    output.getOutput(),
                    output.getNotification().getErrors(),
                    HttpStatus.BAD_REQUEST.value(),
                    thisRoute);
            return ResponseEntity.badRequest().body(response);
        }

        var response = ScheduledMessageResponse.createSuccess(
                "Message scheduled",
                output.getOutput(),
                output.getNotification().getErrors(),
                HttpStatus.OK.value(),
                thisRoute);
        return ResponseEntity.ok().body(response);
    }

}


