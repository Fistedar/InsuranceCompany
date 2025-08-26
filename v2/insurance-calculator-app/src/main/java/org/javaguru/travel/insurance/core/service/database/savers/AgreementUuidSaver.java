package org.javaguru.travel.insurance.core.service.database.savers;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.export.AgreementUuidEntity;
import org.javaguru.travel.insurance.core.repositories.export.AgreementUuidRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AgreementUuidSaver {

    private final AgreementUuidRepository agreementUuidRepository;

    void saveAgreementUuidInDB(AgreementEntity agreementEntity) {
        AgreementUuidEntity agreementUuidEntity = new AgreementUuidEntity();
        agreementUuidEntity.setUuid(agreementEntity.getUuid());
        agreementUuidEntity.setTimeDate(LocalDateTime.now());
        agreementUuidRepository.save(agreementUuidEntity);
    }
}
