package org.javaguru.travel.insurance.core.repositories.pdfFiles;

import org.javaguru.travel.insurance.core.domain.pdfFiles.PdfFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfFilesRepository extends JpaRepository<PdfFilesEntity, String> {

}
