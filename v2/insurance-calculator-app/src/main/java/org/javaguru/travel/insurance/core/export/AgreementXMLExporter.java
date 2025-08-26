package org.javaguru.travel.insurance.core.export;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.repositories.export.AgreementUuidRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AgreementXMLExporter {

    @Value("${agreement.xml.exporter.job.path}")
    private String path;

    private final XmlMapper xmlMapper;
    private final AgreementUuidRepository repository;

    public AgreementXMLExporter(AgreementUuidRepository repository) {
        this.repository = repository;
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.registerModule(new JavaTimeModule());
        this.xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void serialAgreementToXML(AgreementDTO agreement) {
        try {
            xmlMapper.writeValue(new File(path + agreement.getUuid() + ".xml"), agreement);
            repository.deleteByUuid(agreement.getUuid());
        } catch (IOException e) {
            throw new RuntimeException("Could not serialize agreement to XML", e);
        }

    }
}
