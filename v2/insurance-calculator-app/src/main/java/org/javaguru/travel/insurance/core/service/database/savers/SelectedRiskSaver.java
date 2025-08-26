package org.javaguru.travel.insurance.core.service.database.savers;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entity.SelectedRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entity.SelectedRisksRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SelectedRiskSaver {

    private final SelectedRisksRepository selectedRisksRepository;

    void saveSelectedRisk(AgreementDTO agreementDTO, AgreementEntity agreementEntity) {
        agreementDTO.getSelectedRisks()
                .forEach(selectedRisk -> selectedRisksRepository.save(createRiskEntity(selectedRisk, agreementEntity)));
    }

    private SelectedRiskEntity createRiskEntity(String selectedRisk, AgreementEntity agreementEntity) {
        SelectedRiskEntity selectedRisksEntity = new SelectedRiskEntity();
        selectedRisksEntity.setAgreement(agreementEntity);
        selectedRisksEntity.setSelectedRisk(selectedRisk);
        return selectedRisksEntity;
    }

}
