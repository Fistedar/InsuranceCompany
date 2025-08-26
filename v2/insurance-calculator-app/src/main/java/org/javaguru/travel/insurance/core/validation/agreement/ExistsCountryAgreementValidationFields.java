package org.javaguru.travel.insurance.core.validation.agreement;


import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class ExistsCountryAgreementValidationFields extends TravelAgreementValidationFieldsImpl {

    private static final String COUNTRY = "COUNTRY";
    private static final String ERROR_CODE = "ERROR_CODE_11";

    private final ValidationErrorFactory errorFactory;
    private final ClassifierValueRepository classifierValueRepository;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return (countryIsNotEmpty(agreementDTO)
                && countryNotExistsInDatabase(agreementDTO))
                ? Optional.of(buildValidationError(agreementDTO.getCountry()))
                : Optional.empty();
    }

    private boolean countryNotExistsInDatabase(AgreementDTO agreementDTO) {
        return classifierValueRepository.findByClassifierTitleAndIc(COUNTRY, agreementDTO.getCountry()).isEmpty();
    }

    private boolean countryIsNotEmpty(AgreementDTO agreementDTO) {
        return agreementDTO.getCountry() != null && !agreementDTO.getCountry().isBlank();
    }

    private ValidationErrorDTO buildValidationError(String riskIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_COUNTRY", riskIc);
        return errorFactory.buildError(ERROR_CODE, placeholder);
    }
}
