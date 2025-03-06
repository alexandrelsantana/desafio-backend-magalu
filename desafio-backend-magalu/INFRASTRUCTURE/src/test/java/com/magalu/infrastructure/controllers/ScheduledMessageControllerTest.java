package com.magalu.infrastructure.controllers;

import com.magalu.application.use_cases.scheduled_message.ScheduledMessageUseCaseAbstract;
import com.magalu.domain.ValueObject.message.MessageGatewayInterface;
import com.magalu.domain.entity.scheduled_message.ScheduledMessageGatewayInterface;
import groovy.util.logging.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class ScheduledMessageControllerTest {

    private static final Logger log = LogManager.getLogger(ScheduledMessageControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Mock
    MessageGatewayInterface messageGateway;
    @Mock
    ScheduledMessageGatewayInterface scheduledMessageGateway;
    @Autowired
    ScheduledMessageUseCaseAbstract scheduledMessageUseCase;

    @Test
    public void test2Return200() throws Exception {

        String request = """            
            {
                "message": "Mensagem de Teste",
                "to": "test@example.com",
                "scheduled_time": "01/01/2999 00:00"
            }
            """;

        var response = mockMvc.perform(post("/api/create-scheduler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        response.andExpect(status().isOk());
    }

    @Test
    public void test2Return400() throws Exception {

        String request = """            
            {
                "message": "Mensagem de Teste",
                "to": "test@example.com",
                "scheduled_time": "2026-02-23T23:32:00.028413660"
            }
            """;

        var response = mockMvc.perform(post("/api/create-scheduler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        response.andExpect(status().isBadRequest());
    }

}