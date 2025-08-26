package org.javaguru.travel.insurance.dto.internal;

import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.dto.TravelRisk;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v2.PersonResponseDTO;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ResponseMapperInternal {

    public TravelGetAgreementResponse createResponse(TravelGetAgreementCoreResult result) {
        return result.hasErrors()
                ? buildErrorResponse(result)
                : buildSuccessResponse(result);
    }

    private TravelGetAgreementResponse buildSuccessResponse(TravelGetAgreementCoreResult result) {
        AgreementDTO agreementDTO = result.getAgreement();
        return TravelGetAgreementResponse.builder()
                .agreementDateFrom(agreementDTO.getAgreementDateFrom())
                .agreementDateTo(agreementDTO.getAgreementDateTo())
                .uuid(agreementDTO.getUuid())
                .country(agreementDTO.getCountry())
                .agreementPremium(agreementDTO.getAgreementPremium())
                .persons(mapPersonResponse(agreementDTO))
                .build();
    }

    private TravelGetAgreementResponse buildErrorResponse(TravelGetAgreementCoreResult result) {
        List<ValidationError> errors = result.getErrors().stream()
                .map(error -> new ValidationError(error.getErrorCode(), error.getDescription()))
                .toList();
        return new TravelGetAgreementResponse(errors);
    }

    private List<PersonResponseDTO> mapPersonResponse(AgreementDTO agreementDTO) {
        return agreementDTO.getPersons().stream()
                .map(personDTO -> PersonResponseDTO.builder()
                        .personFirstName(personDTO.getPersonFirstName())
                        .personLastName(personDTO.getPersonLastName())
                        .personPremium(getPersonPremium(personDTO))
                        .personCode(personDTO.getPersonCode())
                        .personBirthDate(personDTO.getPersonBirthDate())
                        .medicalRiskLimitLevel(personDTO.getMedicalRiskLimitLevel())
                        .risks(getRisks(personDTO))
                        .build())
                .toList();
    }

    private BigDecimal getPersonPremium(PersonDTO personDTO) {
        return personDTO.getRisks().stream()
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<TravelRisk> getRisks(PersonDTO personDTO) {
        return personDTO.getRisks().stream()
                .map(riskDTO -> new TravelRisk(riskDTO.getRiskIc(), riskDTO.getPremium()))
                .toList();
    }
}
