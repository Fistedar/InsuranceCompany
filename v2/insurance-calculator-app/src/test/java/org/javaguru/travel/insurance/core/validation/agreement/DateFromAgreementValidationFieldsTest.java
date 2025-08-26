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
class DateFromAgreementValidationFieldsTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private ValidationErrorFactory messageService;
    @InjectMocks
    private DateFromAgreementValidationFields validator;

    @Test
    void validateDateFromIsValid() {
        when(agreementDTO.getAgreementDateFrom()).thenReturn(LocalDate.now());

        assertTrue(validator.validate(agreementDTO).isEmpty());
    }

    @Test
    void validateDateFromIsNull() {
        agreementDTO.setAgreementDateFrom(null);
        when(messageService.buildError("ERROR_CODE_3"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_3", "Field agreementDateFrom is empty!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals("ERROR_CODE_3", validator.validate(agreementDTO).orElseThrow().getErrorCode());
        assertEquals("Field agreementDateFrom is empty!", validator.validate(agreementDTO).orElseThrow().getDescription());
    }
}