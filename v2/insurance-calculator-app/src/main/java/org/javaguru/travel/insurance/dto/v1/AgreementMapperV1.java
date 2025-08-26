package org.javaguru.travel.insurance.dto.v1;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class AgreementMapperV1 {

    public AgreementDTO buildAgreementDTO(TravelCalculatePremiumRequestV1 request) {
        return AgreementDTO.builder()
                .agreementDateTo(request.getAgreementDateTo())
                .agreementDateFrom(request.getAgreementDateFrom())
                .uuid(UUID.randomUUID().toString())
                .selectedRisks(request.getSelectedRisks())
                .country(request.getCountry())
                .persons(buildPersonDTO(request))
                .build();
    }

    private List<PersonDTO> buildPersonDTO(TravelCalculatePremiumRequestV1 request) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setMedicalRiskLimitLevel(request.getMedicalRiskLimitLevel());
        personDTO.setPersonFirstName(request.getPersonFirstName());
        personDTO.setPersonLastName(request.getPersonLastName());
        personDTO.setPersonBirthDate(request.getPersonBirthDate());
        personDTO.setPersonCode(request.getPersonCode());
        personDTO.setTravelCost(request.getTravelCost());
        return List.of(personDTO);
    }
}
