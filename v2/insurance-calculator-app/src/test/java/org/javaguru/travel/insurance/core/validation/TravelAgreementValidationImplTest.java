package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelAgreementValidationImplTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private TravelAgreementValidator agreementValidator;
    @Mock
    private TravelPersonValidator personValidator;
    @InjectMocks
    private TravelAgreementValidationImpl travelAgreementValidation;

    @Test
    void validate() {
        when(agreementValidator.validate(agreementDTO))
                .thenReturn(List.of(new ValidationErrorDTO("ERROR_CODE_14", "MedicalRiskLimitLevel is empty!")));
        when(personValidator.validate(agreementDTO))
                .thenReturn(List.of(new ValidationErrorDTO("ERROR_CODE_3", "Field agreementDateFrom is empty!")));

        List<ValidationErrorDTO> errors = travelAgreementValidation.validate(agreementDTO);

        assertNotNull(errors);
        assertEquals(2, errors.size());
    }
}