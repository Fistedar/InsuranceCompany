package org.fistedar.doc.generator.core.util;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Getter;
import org.fistedar.doc.generator.core.dto.AgreementDTO;
import org.fistedar.doc.generator.core.dto.PersonDTO;
import org.fistedar.doc.generator.core.dto.RiskDTO;
import org.fistedar.doc.generator.core.exceptions.PdfGenerationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Getter
@Component
public class PdfGenerator {

    @Value("${pdf.output.dir}")
    private String outputDir;

    public void generateAndSavePdf(AgreementDTO agreement) {

        // Генерация PDF
        Document document = new Document();
        String filename = outputDir + "/agreement_" + agreement.getUuid() + ".pdf";

        try (OutputStream os = new FileOutputStream(filename)) {
            PdfWriter.getInstance(document, os);
            document.open();

            // Основная информация о договоре
            document.add(new Paragraph("Agreement Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("UUID: " + agreement.getUuid()));
            document.add(new Paragraph("Period: " + agreement.getAgreementDateFrom() + " to " + agreement.getAgreementDateTo()));
            document.add(new Paragraph("Country: " + agreement.getCountry()));
            document.add(new Paragraph("Total Premium: " + agreement.getAgreementPremium()));
            document.add(Chunk.NEWLINE);

            // Список рисков
            if (agreement.getSelectedRisks() != null && !agreement.getSelectedRisks().isEmpty()) {
                document.add(new Paragraph("Selected Risks:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
                for (String risk : agreement.getSelectedRisks()) {
                    document.add(new Paragraph("- " + risk));
                }
                document.add(Chunk.NEWLINE);
            }

            // Информация о застрахованных лицах
            if (agreement.getPersons() != null && !agreement.getPersons().isEmpty()) {
                document.add(new Paragraph("Insured Persons:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));

                for (PersonDTO person : agreement.getPersons()) {
                    document.add(new Paragraph("Person: " + person.getPersonFirstName() + " " + person.getPersonLastName(),
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                    document.add(new Paragraph("Code: " + person.getPersonCode()));
                    document.add(new Paragraph("Birth Date: " + person.getPersonBirthDate()));
                    document.add(new Paragraph("Medical Risk Level: " + person.getMedicalRiskLimitLevel()));
                    document.add(new Paragraph("Travel Cost: " + person.getTravelCost()));

                    // Риски для каждого лица
                    if (person.getRisks() != null && !person.getRisks().isEmpty()) {
                        PdfPTable table = new PdfPTable(2);
                        table.addCell(new Phrase("Risk IC", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                        table.addCell(new Phrase("Premium", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

                        for (RiskDTO risk : person.getRisks()) {
                            table.addCell(risk.getRiskIc());
                            table.addCell(risk.getPremium().toString());
                        }
                        document.add(table);
                    }
                    document.add(Chunk.NEWLINE);
                }
            }
            document.close();
        } catch (Exception e) {
            throw new PdfGenerationException("Failed to generate pdf", e);
        }
    }

}