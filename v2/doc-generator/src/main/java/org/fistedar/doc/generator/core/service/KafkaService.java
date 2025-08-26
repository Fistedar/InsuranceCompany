package org.fistedar.doc.generator.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.fistedar.doc.generator.core.dto.AgreementDTO;
import org.fistedar.doc.generator.core.dto.PdfGenerationResult;
import org.fistedar.doc.generator.core.exceptions.KafkaMessageException;
import org.fistedar.doc.generator.core.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    private final PdfGenerator pdfGenerator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.result.pdf-generation}")
    private String resultPdfGenerationTopic;

    @KafkaListener(topics = "${kafka.topic.pdf-generation}")
    public void consumeAgreement(ConsumerRecord<String, String> record) {
        kafkaTemplate.executeInTransaction(ops -> {
            try {
                AgreementDTO agreementDTO = objectMapper.readValue(record.value(), AgreementDTO.class);
                log.info("Received agreement for processing:{}", agreementDTO.getUuid());
                pdfGenerator.generateAndSavePdf(agreementDTO);
                log.info("Sending path and uuid to topic {}, agreement {}", resultPdfGenerationTopic, agreementDTO.getUuid());
                PdfGenerationResult pdfGenerationResult = getPdfGenerationResult(agreementDTO, pdfGenerator.getOutputDir());
                ops.send(resultPdfGenerationTopic, objectMapper.writeValueAsString(pdfGenerationResult));
            } catch (JsonProcessingException e) {
                throw new KafkaMessageException("Failed to parse JSON", e);
            }
            return Boolean.TRUE;
        });
    }

    private PdfGenerationResult getPdfGenerationResult(AgreementDTO agreementDTO, String path) {
        return PdfGenerationResult.builder()
                .uuid(agreementDTO.getUuid())
                .pdfPath(path + "/agreement_" + agreementDTO.getUuid() + ".pdf")
                .build();
    }
}