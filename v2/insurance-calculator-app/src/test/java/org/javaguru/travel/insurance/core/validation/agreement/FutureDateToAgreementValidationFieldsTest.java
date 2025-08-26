package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
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
class FutureDateToAgreementValidationFieldsTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private DateTimeUtil dateTimeUtil;
    @Mock
    private ValidationErrorFactory validationErrorFactory;
    @InjectMocks
    private FutureDateToAgreementValidationFields validator;

    @Test
    void validateValid() {
        when(agreementDTO.getAgreementDateTo()).thenReturn(LocalDate.of(2035, 10, 1));
        when(dateTimeUtil.getCurrentDate()).thenReturn(LocalDate.now());
        assertTrue(validator.validate(agreementDTO).isEmpty());
    }

    @Test
    void validateInvalid() {
        when(agreementDTO.getAgreementDateTo()).thenReturn(LocalDate.of(2023, 10, 12));
        when(dateTimeUtil.getCurrentDate()).thenReturn(LocalDate.now());
        when(validationErrorFactory.buildError("ERROR_CODE_6"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_6", "DateTo from the past!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals("ERROR_CODE_6", validator.validate(agreementDTO).orElseThrow().getErrorCode());
        assertEquals("DateTo from the past!", validator.validate(agreementDTO).orElseThrow().getDescription());
    }
}