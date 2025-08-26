package org.javaguru.travel.insurance.jobs;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.export.AgreementXMLExporter;
import org.javaguru.travel.insurance.core.service.database.loaders.AgreementDTOLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AgreementXmlExporterJob {

    private final AgreementXMLExporter agreementXMLExporter;
    private final AgreementDTOLoader agreementDTOLoader;

    @Value("${agreement.xml.exporter.job.enabled:false}")
    private boolean exporterEnabled;
    @Value("${count.thread.pools.agreement.exporter.job:1}")
    private int threadPools;


    @Scheduled(cron = "0 0 3 * * ?", zone = "Europe/Moscow")
    public void doJob() {
        if (!exporterEnabled) {
            return;
        }
        exportAgreementToXMLAndDeleteUuidInDB(threadPools);
    }

    private void exportAgreementToXMLAndDeleteUuidInDB(int threadPoolSize) {
        LocalDate yesterday = LocalDate.now();
        LocalDateTime startOfDay = yesterday.atStartOfDay();
        LocalDateTime endOfDay = yesterday.atTime(23, 59, 59);
        List<AgreementDTO> exportAgreements = agreementDTOLoader.loadAgreementDTOByDateTime(startOfDay, endOfDay);

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        try {
            exportAgreements.forEach(agreement ->
                    executor.submit(() -> agreementXMLExporter.serialAgreementToXML(agreement))
            );
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
