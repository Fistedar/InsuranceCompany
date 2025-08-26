package org.javaguru.travel.insurance.core.service.database.loaders;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entity.PersonEntity;
import org.javaguru.travel.insurance.core.domain.entity.SelectedRiskEntity;
import org.javaguru.travel.insurance.core.domain.export.AgreementUuidEntity;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementPersonRepository;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementPersonRiskRepository;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementRepository;
import org.javaguru.travel.insurance.core.repositories.entity.SelectedRisksRepository;
import org.javaguru.travel.insurance.core.repositories.export.AgreementUuidRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AgreementDTOLoader {

    private final AgreementPersonRiskRepository agreementPersonRiskRepository;
    private final AgreementRepository agreementRepository;
    private final AgreementPersonRepository agreementPersonRepository;
    private final SelectedRisksRepository selectedRisksRepository;
    private final AgreementUuidRepository agreementUuidRepository;

    public AgreementDTO loadAgreementDTOByUuid(String uuid) {
        AgreementEntity entity = agreementRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Can't find uuid: " + uuid));
        return buildAgreementDTO(entity);
    }

    public List<AgreementDTO> loadAgreementDTOByDateTime(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        List<AgreementUuidEntity> AgreementUuidEntity = agreementUuidRepository.findByTimeDate(dateTimeFrom, dateTimeTo);
        return AgreementUuidEntity.stream()
                .map(entity -> loadAgreementDTOByUuid(entity.getUuid()))
                .toList();
    }

    private AgreementDTO buildAgreementDTO(AgreementEntity agreementEntity) {
        return AgreementDTO.builder()
                .agreementPremium(agreementEntity.getPremium())
                .country(agreementEntity.getCountry())
                .agreementDateFrom(agreementEntity.getDateFrom())
                .agreementDateTo(agreementEntity.getDateTo())
                .uuid(agreementEntity.getUuid())
                .persons(loadPersonDTOs(agreementEntity))
                .selectedRisks(loadSelectedRisks(agreementEntity))
                .build();
    }

    private PersonDTO buildPersonDTO(PersonEntity personEntity, String medicalRisk, List<RiskDTO> riskDTOs) {
       return PersonDTO.builder()
                .personCode(personEntity.getPersonCode())
                .medicalRiskLimitLevel(medicalRisk)
                .personBirthDate(personEntity.getBirthDate())
                .personFirstName(personEntity.getFirstName())
                .personLastName(personEntity.getLastName())
                .risks(riskDTOs)
                .build();
    }

    private List<PersonDTO> loadPersonDTOs(AgreementEntity agreementEntity) {
        return agreementPersonRepository.findByAgreement(agreementEntity).stream()
                .map(entity -> buildPersonDTO(
                        entity.getPerson(),
                        entity.getMedicalRisk(),
                        loadRiskDTOs(entity)))
                .toList();
    }

    private List<RiskDTO> loadRiskDTOs(AgreementPersonEntity agreementPersonEntity) {
        return agreementPersonRiskRepository.findByAgreementPerson(agreementPersonEntity).stream()
                .map(entity -> RiskDTO.builder()
                        .riskIc(entity.getRiskIc())
                        .premium(entity.getPremium())
                        .build())
                .toList();
    }

    private List<String> loadSelectedRisks(AgreementEntity agreementEntity) {
        return selectedRisksRepository.findByAgreement(agreementEntity).stream()
                .map(SelectedRiskEntity::getSelectedRisk)
                .toList();
    }

}
