package org.fistedar.doc.generator.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.fistedar.doc.generator.core.dto.AgreementDTO;
import org.fistedar.doc.generator.core.dto.PdfGenerationResult;
import org.fistedar.doc.generator.core.util.PdfGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private PdfGenerator pdfGenerator;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaOperations<String, String> kafkaOperations;

    @InjectMocks
    private KafkaService kafkaService;

    @Test
    @SuppressWarnings("unchecked")
    public void testConsumeAgreement_Success() throws Exception {
        // 1. Подготовка данных
        String testTopic = "test-topic";
        String resultTopic = "result-topic";
        String testMessage = "{\"uuid\":\"test123\"}";
        ConsumerRecord<String, String> record = new ConsumerRecord<>(testTopic, 0, 0, "key", testMessage);

        AgreementDTO agreementDTO = new AgreementDTO();
        agreementDTO.setUuid("test123");

        // 2. Настройка моков
        when(objectMapper.readValue(testMessage, AgreementDTO.class)).thenReturn(agreementDTO);
        when(pdfGenerator.getOutputDir()).thenReturn("/output");
        when(objectMapper.writeValueAsString(any(PdfGenerationResult.class)))
                .thenReturn("{\"pdfPath\":\"/output/agreement_test123.pdf\"}");

        // 3. Создаем типизированный мок для KafkaOperations
        CompletableFuture<SendResult<String, String>> future =
                CompletableFuture.completedFuture(mock(SendResult.class));

        when(kafkaOperations.send(eq(resultTopic), anyString())).thenReturn(future);

        // 4. Мокируем executeInTransaction с явным указанием типов
        when(kafkaTemplate.executeInTransaction(any(KafkaTemplate.OperationsCallback.class)))
                .thenAnswer(invocation -> {
                    KafkaTemplate.OperationsCallback<String, String, Boolean> callback =
                            invocation.getArgument(0);
                    return callback.doInOperations(kafkaOperations);
                });

        // 5. Устанавливаем значение для @Value поля
        ReflectionTestUtils.setField(kafkaService, "resultPdfGenerationTopic", resultTopic);

        // 6. Вызов тестируемого метода
        kafkaService.consumeAgreement(record);

        // 7. Проверки
        verify(pdfGenerator).generateAndSavePdf(agreementDTO);
        verify(kafkaTemplate).executeInTransaction(any(KafkaTemplate.OperationsCallback.class));
        verify(objectMapper).writeValueAsString(any(PdfGenerationResult.class));
    }
}