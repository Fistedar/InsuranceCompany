package org.javaguru.travel.insurance.core.domain.pdfFiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pdf_files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PdfFilesEntity {

    @Id
    @Column(name = "agreement_uuid", nullable = false)
    private String agreementUuid;

    @Column(name = "path", nullable = false)
    private String path;
}
