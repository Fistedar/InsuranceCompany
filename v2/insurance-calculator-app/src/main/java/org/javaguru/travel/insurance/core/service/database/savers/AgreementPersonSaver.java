package org.javaguru.travel.insurance.core.service.database.savers;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entity.PersonEntity;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementPersonRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AgreementPersonSaver {

    private final AgreementPersonRepository agreementPersonRepository;

    AgreementPersonEntity createAndSaveAgreementPerson(PersonDTO personDTO, AgreementEntity agreementEntity, PersonEntity personEntity) {
        AgreementPersonEntity agreementPerson = new AgreementPersonEntity();
        agreementPerson.setAgreement(agreementEntity);
        agreementPerson.setPerson(personEntity);
        agreementPerson.setMedicalRisk(personDTO.getMedicalRiskLimitLevel());
        return agreementPersonRepository.save(agreementPerson);
    }
}
