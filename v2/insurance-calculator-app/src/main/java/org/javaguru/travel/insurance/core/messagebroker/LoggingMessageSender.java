package org.javaguru.travel.insurance.core.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile({"h2", "mysql-local"})
class LoggingMessageSender  implements MessageSender {

    private final Logger logger = LoggerFactory.getLogger("CONSOLE");
    private final ObjectMapper objectMapper;

    @Override
    public void send(AgreementDTO agreementDTO) {
        try {
            logger.info("Agreement : {}", objectMapper.writeValueAsString(agreementDTO));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }
}
