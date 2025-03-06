package com.magalu.infrastructure.scheduled_message;

import com.magalu.domain.ValueObject.message.Message;
import com.magalu.domain.ValueObject.message.MessageGatewayInterface;
import org.springframework.stereotype.Component;

@Component
public class MessageGateway implements MessageGatewayInterface {
    @Override
    public void send(Message message) {

    }
}
