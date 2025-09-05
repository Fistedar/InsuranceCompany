package org.javaguru.travel.insurance.core.messagebroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.exceptions.messagebroker.KafkaMessageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("mysql-container")
public class KafkaMessageSender implements MessageSender {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.pdf-generation}")
    private String topic;

    @Override
    public void send(AgreementDTO agreementDTO) {
        try {
            log.info("Sending agreement to topic '{}': {}", topic, agreementDTO.getUuid());
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(agreementDTO));
            log.info("Successfully sent agreement: '{}'", agreementDTO.getUuid());
        } catch (Exception e) {
            log.error("Error sending agreement {}: {}", agreementDTO.getUuid(), e.getMessage(), e);
            throw new KafkaMessageException("Failed to send agreement to Kafka", e);
        }
    }
}
