package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateFromBeforeDateToAgreementValidationFieldsTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private ValidationErrorFactory validationErrorFactory;
    @InjectMocks
    private DateFromBeforeDateToAgreementValidationFields validator;


    @Test
    void dateIsInvalid() {
        when(agreementDTO.getAgreementDateTo()).thenReturn(LocalDate.of(2025, 10, 10));
        when(agreementDTO.getAgreementDateFrom()).thenReturn(LocalDate.of(2025, 10, 15));
        when(validationErrorFactory.buildError("ERROR_CODE_7"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_7", "DateTo before DateFrom!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals("ERROR_CODE_7", validator.validate(agreementDTO).orElseThrow().getErrorCode());
        assertEquals("DateTo before DateFrom!", validator.validate(agreementDTO).orElseThrow().getDescription());
    }

    @Test
    void dateIsValid() {
        when(agreementDTO.getAgreementDateFrom()).thenReturn(LocalDate.of(2025, 10, 10));
        when(agreementDTO.getAgreementDateTo()).thenReturn(LocalDate.of(2025, 10, 15));

        assertTrue(validator.validate(agreementDTO).isEmpty());
    }
}