package org.javaguru.travel.insurance.core.service.database.savers;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AgreementSaver {

    private final AgreementRepository agreementRepository;

    AgreementEntity saveAndCreateAgreementEntity(AgreementDTO agreementDTO) {
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setUuid(agreementDTO.getUuid());
        agreementEntity.setDateFrom(agreementDTO.getAgreementDateFrom());
        agreementEntity.setDateTo(agreementDTO.getAgreementDateTo());
        agreementEntity.setPremium(agreementDTO.getAgreementPremium());
        agreementEntity.setCountry(agreementDTO.getCountry());
        return agreementRepository.save(agreementEntity);
    }
}
