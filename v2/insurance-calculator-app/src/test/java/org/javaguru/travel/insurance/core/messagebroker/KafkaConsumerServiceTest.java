package org.javaguru.travel.insurance.core.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.javaguru.travel.insurance.core.api.dto.PdfResultDTO;
import org.javaguru.travel.insurance.core.domain.pdfFiles.PdfFilesEntity;
import org.javaguru.travel.insurance.core.repositories.pdfFiles.PdfFilesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private PdfFilesRepository pdfFilesRepository;
    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    private final String testTopic = "testTopic";
    private final String jsonValue = "{\"agreementUuid\":\"test-uuid-123\",\"path\":\"/files/test.pdf\"}";

    @Test
    void shouldSavePathPdfFiles_whenUuidNotExistInDatabase() throws JsonProcessingException {
        ConsumerRecord<String, String> record = new ConsumerRecord<>(testTopic, 0, 0, "key", jsonValue);
        PdfResultDTO resultDTO = new PdfResultDTO("test-uuid-123", "/files/test.pdf");

        when(objectMapper.readValue(jsonValue, PdfResultDTO.class)).thenReturn(resultDTO);
        when(pdfFilesRepository.existsById("test-uuid-123")).thenReturn(Boolean.FALSE);
        ArgumentCaptor<PdfFilesEntity> pdfFilesEntityArgumentCaptor = ArgumentCaptor.forClass(PdfFilesEntity.class);

        kafkaConsumerService.savePathPdfFiles(record);

        verify(objectMapper).readValue(jsonValue, PdfResultDTO.class);
        verify(pdfFilesRepository).existsById("test-uuid-123");
        verify(pdfFilesRepository).save(pdfFilesEntityArgumentCaptor.capture());

        PdfFilesEntity pdfFilesEntity = pdfFilesEntityArgumentCaptor.getValue();
        assertEquals("test-uuid-123", pdfFilesEntity.getAgreementUuid());
        assertEquals("/files/test.pdf", pdfFilesEntity.getPath());
    }

    @Test
    void shouldDontSavePathPdfFiles_whenUuidNotExistInDatabase() throws JsonProcessingException {
        ConsumerRecord<String, String> record = new ConsumerRecord<>(testTopic, 0, 0, "key", jsonValue);
        PdfResultDTO resultDTO = new PdfResultDTO("test-uuid-123", "/files/test.pdf");

        when(pdfFilesRepository.existsById("test-uuid-123")).thenReturn(Boolean.TRUE);
        when(objectMapper.readValue(jsonValue, PdfResultDTO.class)).thenReturn(resultDTO);

        kafkaConsumerService.savePathPdfFiles(record);

        verify(pdfFilesRepository).existsById("test-uuid-123");
        verify(objectMapper).readValue(jsonValue, PdfResultDTO.class);
        verify(pdfFilesRepository, never()).save(any(PdfFilesEntity.class));
    }

    @Test
    void shouldThrowException_whenGetWrongJson() throws JsonProcessingException {
        ConsumerRecord<String, String> record = new ConsumerRecord<>(testTopic, 0, 0, "key", jsonValue);
        JsonProcessingException jsonProcessingException = mock(JsonProcessingException.class);

        when(objectMapper.readValue(jsonValue, PdfResultDTO.class)).thenThrow(jsonProcessingException);

        assertThrows(RuntimeException.class, () -> kafkaConsumerService.savePathPdfFiles(record));
        verify(objectMapper).readValue(jsonValue, PdfResultDTO.class);
        verify(pdfFilesRepository, never()).existsById("test-uuid-123");
        verify(pdfFilesRepository, never()).save(any(PdfFilesEntity.class));
    }
}