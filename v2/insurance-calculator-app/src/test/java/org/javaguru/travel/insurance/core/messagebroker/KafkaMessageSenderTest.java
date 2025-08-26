package org.javaguru.travel.insurance.core.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.exceptions.messagebroker.KafkaMessageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaMessageSenderTest {

    @Mock
    AgreementDTO agreementDTO;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaMessageSender kafkaMessageSender;

    private final String testTopic = "testTopic";
    private final String testMessage = "testMessage";

    @Test
    @SuppressWarnings("unchecked")
    void shouldSendMessageSuccessfully() throws JsonProcessingException {
        ReflectionTestUtils.setField(kafkaMessageSender, "topic", testTopic);
        CompletableFuture<SendResult<String,String>> future = CompletableFuture.completedFuture(mock(SendResult.class));

        when(objectMapper.writeValueAsString(agreementDTO)).thenReturn(testMessage);
        when(kafkaTemplate.send(testTopic,testMessage)).thenReturn(future);

        kafkaMessageSender.send(agreementDTO);

        verify(kafkaTemplate).send(testTopic,testMessage);
        verify(objectMapper).writeValueAsString(agreementDTO);
    }

    @Test
    void shouldThrowException_whenSendFailed() throws JsonProcessingException {
        ReflectionTestUtils.setField(kafkaMessageSender, "topic", testTopic);

        when(objectMapper.writeValueAsString(agreementDTO)).thenReturn(testMessage);
        when(kafkaTemplate.send(testTopic,testMessage)).thenThrow(new RuntimeException());

        assertThrows(KafkaMessageException.class, () -> kafkaMessageSender.send(agreementDTO));
        verify(kafkaTemplate).send(testTopic,testMessage);
    }
}