package org.javaguru.travel.insurance.core.service.database.savers;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgreementFactory {

    private final AgreementSaver agreementSaver;
    private final AgreementPersonSaver agreementPersonSaver;
    private final AgreementPersonRiskSaver agreementPersonRiskSaver;
    private final SelectedRiskSaver selectedRiskSaver;
    private final PersonSaver personSaver;
    private final AgreementUuidSaver agreementUuidSaver;

    public void saveAgreementInDatabase(AgreementDTO agreementDTO) {
        AgreementEntity agreementEntity = agreementSaver.saveAndCreateAgreementEntity(agreementDTO);
        selectedRiskSaver.saveSelectedRisk(agreementDTO, agreementEntity);
        agreementUuidSaver.saveAgreementUuidInDB(agreementEntity);
        saveAllData(agreementDTO, agreementEntity);
    }

    private void saveAllData(AgreementDTO agreementDTO, AgreementEntity agreementEntity) {
        agreementDTO.getPersons().forEach(personDTO -> {
            PersonEntity personEntity = personSaver.createAndSaveEntity(personDTO);
            AgreementPersonEntity agreementPersonEntity = agreementPersonSaver.createAndSaveAgreementPerson(personDTO, agreementEntity, personEntity);
            personDTO.getRisks().forEach(riskDTO ->
                    agreementPersonRiskSaver.saveAndCreateAgreementPersonRisk(agreementPersonEntity, riskDTO));
        });
    }

}
