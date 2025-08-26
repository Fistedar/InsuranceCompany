package org.fistedar.doc.generator.core.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fistedar.doc.generator.core.dto.AgreementDTO;
import org.fistedar.doc.generator.core.dto.PersonDTO;
import org.fistedar.doc.generator.core.dto.RiskDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PdfGeneratorTest {

    @TempDir
    File tempDir;

    @InjectMocks
    private PdfGenerator pdfGenerator;

    @Test
    void generateAndSavePdf_shouldCreateFile(){
        // Устанавливаем временную директорию
        ReflectionTestUtils.setField(pdfGenerator, "outputDir", tempDir.getAbsolutePath());

        AgreementDTO agreement = createTestAgreement();

        pdfGenerator.generateAndSavePdf(agreement);

        File expectedFile = new File(tempDir, "agreement_test-uuid.pdf");
        assertTrue(expectedFile.exists());
        assertTrue(expectedFile.length() > 0);
    }

    @Test
    void generateAndSavePdf_shouldContainCorrectData() throws Exception {
        ReflectionTestUtils.setField(pdfGenerator, "outputDir", tempDir.getAbsolutePath());

        AgreementDTO agreement = createTestAgreement();

        pdfGenerator.generateAndSavePdf(agreement);

        File pdfFile = new File(tempDir, "agreement_test-uuid.pdf");
        try (PDDocument doc = PDDocument.load(pdfFile)) {
            String text = new PDFTextStripper().getText(doc);

            // Проверяем основные данные
            assertTrue(text.contains("Agreement Details:"));
            assertTrue(text.contains("UUID: test-uuid"));
            assertTrue(text.contains("Country: Test"));
            assertTrue(text.contains("Total Premium: 1000.0"));

            // Проверяем риски
            assertTrue(text.contains("Selected Risks:"));
            assertTrue(text.contains("- Risk 1"));
            assertTrue(text.contains("- Risk 2"));

            // Проверяем информацию о лице
            assertTrue(text.contains("Person: John Doe"));
            assertTrue(text.contains("Code: 12345"));
            assertTrue(text.contains("Birth Date: 1990-01-01"));

            // Проверяем таблицу рисков
            assertTrue(text.contains("Risk IC"));
            assertTrue(text.contains("Premium"));
            assertTrue(text.contains("IC01"));
            assertTrue(text.contains("50.0"));
        }
    }

    @Test
    void generateAndSavePdf_shouldHandleEmptyLists() throws Exception {
        ReflectionTestUtils.setField(pdfGenerator, "outputDir", tempDir.getAbsolutePath());

        AgreementDTO agreement = createTestAgreement();
        agreement.setSelectedRisks(null);
        agreement.setPersons(null);

        pdfGenerator.generateAndSavePdf(agreement);

        File pdfFile = new File(tempDir, "agreement_test-uuid.pdf");
        String content = new String(Files.readAllBytes(pdfFile.toPath()));

        // Проверяем отсутствие секций
        assertFalse(content.contains("Selected Risks:"));
        assertFalse(content.contains("Insured Persons:"));
    }

    private AgreementDTO createTestAgreement() {
        AgreementDTO agreement = new AgreementDTO();
        agreement.setUuid("test-uuid");
        agreement.setAgreementDateFrom(LocalDate.of(2023, 1, 1));
        agreement.setAgreementDateTo(LocalDate.of(2023, 12, 31));
        agreement.setCountry("Test");
        agreement.setAgreementPremium(new BigDecimal("1000.0"));
        agreement.setSelectedRisks(Arrays.asList("Risk 1", "Risk 2"));

        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("John");
        person.setPersonLastName("Doe");
        person.setPersonCode("12345");
        person.setPersonBirthDate(LocalDate.of(1990, 1, 1));
        person.setMedicalRiskLimitLevel("HIGH");
        person.setTravelCost(BigDecimal.valueOf(500.0));

        RiskDTO risk = new RiskDTO();
        risk.setRiskIc("IC01");
        risk.setPremium(BigDecimal.valueOf(50.0));
        person.setRisks(List.of(risk));

        agreement.setPersons(List.of(person));
        return agreement;
    }
}