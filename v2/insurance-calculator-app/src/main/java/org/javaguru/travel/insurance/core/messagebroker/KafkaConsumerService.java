package org.javaguru.travel.insurance.core.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.javaguru.travel.insurance.core.api.dto.PdfResultDTO;
import org.javaguru.travel.insurance.core.domain.pdfFiles.PdfFilesEntity;
import org.javaguru.travel.insurance.core.repositories.pdfFiles.PdfFilesRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("mysql-container")
public class KafkaConsumerService {

    private final PdfFilesRepository pdfFilesRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.result.pdf-generation}")
    public void savePathPdfFiles(ConsumerRecord<String, String> record) {
        log.info("Received message - Key: {}, Value: {}", record.key(), record.value());
        PdfFilesEntity pdfFilesEntity = jsonMapper(record);
        if (pdfFilesRepository.existsById(pdfFilesEntity.getAgreementUuid())){
            return;
        }
        pdfFilesRepository.save(pdfFilesEntity);
    }

    private PdfFilesEntity jsonMapper(ConsumerRecord<String, String> record) {
        try {
            PdfResultDTO resultDTO = objectMapper.readValue(record.value(), PdfResultDTO.class);
            return new PdfFilesEntity(resultDTO.getUuid(), resultDTO.getPdfPath());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
