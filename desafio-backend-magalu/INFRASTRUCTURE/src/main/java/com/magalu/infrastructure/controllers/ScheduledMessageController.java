package com.magalu.infrastructure.controllers;

import com.google.gson.Gson;
import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageInput;
import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageOutput;
import com.magalu.application.use_cases.cancel_scheduled_message.CancelScheduledMessageUseCaseAbstract;
import com.magalu.application.use_cases.retrieve_scheduled.RetrieveScheduledMessageInput;
import com.magalu.application.use_cases.retrieve_scheduled.RetrieveScheduledMessageOutput;
import com.magalu.application.use_cases.retrieve_scheduled.RetrieveScheduledMessageUseCase;
import com.magalu.application.use_cases.retrieve_scheduled.RetrieveScheduledMessageUseCaseAbstract;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageInput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageOutput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCaseAbstract;
import com.magalu.infrastructure.scheduled_message.models.*;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Validated
@RequestMapping("/api")
public class ScheduledMessageController implements ScheduledMessageControllerAPI{

    private final Gson gson = new Gson();

    final ScheduledMessageUseCaseAbstract scheduledMessageUseCase;
    final CancelScheduledMessageUseCaseAbstract cancelScheduledMessageUseCase;
    final RetrieveScheduledMessageUseCaseAbstract retrieveScheduledMessageUseCase;

    public ScheduledMessageController(
            ScheduledMessageUseCaseAbstract scheduledMessageUseCase,
            CancelScheduledMessageUseCaseAbstract cancelScheduledMessageUseCase,
            RetrieveScheduledMessageUseCaseAbstract retrieveScheduledMessageUseCase
    ) {
        this.scheduledMessageUseCase = scheduledMessageUseCase;
        this.cancelScheduledMessageUseCase = cancelScheduledMessageUseCase;
        this.retrieveScheduledMessageUseCase = retrieveScheduledMessageUseCase;
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

    @PostMapping("/cancel-scheduler")
    @Override
    public ResponseEntity<?> cancelScheduler(CancelScheduledMessageRequest request) {
        var thisRoute = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        var input = new CancelScheduledMessageInput(
                request.uuid()
        );
        CancelScheduledMessageOutput output = cancelScheduledMessageUseCase.execute(input);

        if (output.hasError()){
            var response = CancelScheduledMessageResponse.createFailed(
                    "Message not Cancelled",
                    output.getOutput(),
                    output.getNotification().getErrors(),
                    HttpStatus.BAD_REQUEST.value(),
                    thisRoute);
            return ResponseEntity.badRequest().body(response);
        }

        var response = ScheduledMessageResponse.createSuccess(
                "Message Cancelled",
                output.getOutput(),
                output.getNotification().getErrors(),
                HttpStatus.OK.value(),
                thisRoute);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/retrieve-scheduler")
    @Override
    public ResponseEntity<?> retrieveScheduler(String uuid) {
        var thisRoute = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        var input = new RetrieveScheduledMessageInput(uuid);
        RetrieveScheduledMessageOutput output = retrieveScheduledMessageUseCase.execute(input);

        if (output.hasError()){
            var response = RetrieveScheduledMessageResponse.createFailed(
                    "Message not retrieve",
                    output.getOutput(),
                    output.getNotification().getErrors(),
                    HttpStatus.BAD_REQUEST.value(),
                    thisRoute);
            return ResponseEntity.badRequest().body(response);
        }

        var response = RetrieveScheduledMessageResponse.createSuccess(
                "Message retrieve",
                output.getOutput(),
                output.getNotification().getErrors(),
                HttpStatus.OK.value(),
                thisRoute);
        return ResponseEntity.ok().body(response);
    }

}


