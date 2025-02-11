package com.magalu.infrastructure;

import com.magalu.application.use_cases.scheduled_message.ScheduledMessageInput;
import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCase;
import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.ValueObject.message.MessageGateway;
import com.magalu.domain.entity.scheduled_message.ScheduledMessage;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGateway;
import com.magalu.domain.utils.TimeUtil;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

//        final LocalDateTime scheduledTime = LocalDateTime.now().plusMinutes(1);
//        final String dateTimeStr = TimeUtil.timeToString(scheduledTime);
//        final String MESSAGE = "Message";
////        dateTimeStr = "25/08/24-00:01";
//
//        final var useCase = new ScheduledMessageUseCase(new ScheduledMessageGateway() {
//            @Override
//            public ScheduledMessage save(ScheduledMessage scheduledMessage) {
//                return null;
//            }
//        }, new MessageGateway() {
//            @Override
//            public void send(Message message) {
//            }
//        });
//
//        var input = ScheduledMessageInput.with(
//                dateTimeStr,
//                MESSAGE,
//                "teste@teste.com"
//        );
//
//        var output = useCase.execute(input);
//        System.out.println("");

    }
}