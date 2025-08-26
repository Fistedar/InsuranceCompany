package org.javaguru.travel.insurance.core.service.database.savers;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementPersonRiskRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AgreementPersonRiskSaver {

    private final AgreementPersonRiskRepository agreementPersonRiskRepository;

    void saveAndCreateAgreementPersonRisk(AgreementPersonEntity agreementPersonEntity, RiskDTO riskDTO) {
        AgreementPersonRiskEntity agreementPersonRiskEntity = new AgreementPersonRiskEntity();
        agreementPersonRiskEntity.setAgreementPerson(agreementPersonEntity);
        agreementPersonRiskEntity.setPremium(riskDTO.getPremium());
        agreementPersonRiskEntity.setRiskIc(riskDTO.getRiskIc());
        agreementPersonRiskRepository.save(agreementPersonRiskEntity);
    }

}
