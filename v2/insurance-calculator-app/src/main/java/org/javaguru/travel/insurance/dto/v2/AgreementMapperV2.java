package org.javaguru.travel.insurance.dto.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.springframework.stereotype.Component;

@Component
public class AgreementMapperV2 {

    public AgreementDTO buildAgreementDTO(TravelCalculatePremiumRequestV2 request) {
        return AgreementDTO.builder()
                .agreementDateTo(request.getAgreementDateTo())
                .agreementDateFrom(request.getAgreementDateFrom())
                .selectedRisks(request.getSelectedRisks())
                .uuid(UUID.randomUUID().toString())
                .country(request.getCountry())
                .persons(buildListPersonDTO(request))
                .build();
    }

    private List<PersonDTO> buildListPersonDTO(TravelCalculatePremiumRequestV2 request) {
        return (request.getPersonList() == null)
                ? new ArrayList<>()
                : request.getPersonList().stream()
                .map(this::buildPersonDTO)
                .toList();
    }

    private PersonDTO buildPersonDTO(PersonRequestDTO personRequestDTO) {
        return PersonDTO.builder()
                .personFirstName(personRequestDTO.getPersonFirstName())
                .personLastName(personRequestDTO.getPersonLastName())
                .personBirthDate(personRequestDTO.getPersonBirthDate())
                .medicalRiskLimitLevel(personRequestDTO.getMedicalRiskLimitLevel())
                .personCode(personRequestDTO.getPersonCode())
                .travelCost(personRequestDTO.getTravelCost())
                .build();
    }
}
