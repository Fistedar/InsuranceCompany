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
class FutureDateFromAgreementValidationFieldsTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private ValidationErrorFactory validationErrorFactory;
    @Mock
    private DateTimeUtil dateTimeUtil;
    @InjectMocks
    private FutureDateFromAgreementValidationFields validator;

    @Test
    void validateValid() {
        when(agreementDTO.getAgreementDateFrom()).thenReturn(LocalDate.of(2035, 10, 10));
        when(dateTimeUtil.getCurrentDate()).thenReturn(LocalDate.of(2010, 4, 4));

        assertTrue(validator.validate(agreementDTO).isEmpty());
    }

    @Test
    void validateInvalid() {
        when(agreementDTO.getAgreementDateFrom()).thenReturn(LocalDate.of(2024, 10, 10));
        when(dateTimeUtil.getCurrentDate()).thenReturn(LocalDate.now());
        when(validationErrorFactory.buildError("ERROR_CODE_5"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_5", "DateFrom from the past!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals("ERROR_CODE_5", validator.validate(agreementDTO).orElseThrow().getErrorCode());
        assertEquals("DateFrom from the past!", validator.validate(agreementDTO).orElseThrow().getDescription());
    }
}