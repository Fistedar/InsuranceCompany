package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryAgreementValidationFieldsTest {
    private static final String ERROR = "ERROR_CODE_10";

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private EmptyCountryAgreementValidationFields validator;

    @Test
    void shouldReturnError_whenGetEmptyCountry() {
        when(agreementDTO.getCountry()).thenReturn(" ");
        when(errorFactory.buildError(ERROR))
                .thenReturn(new ValidationErrorDTO(ERROR, "Field country is empty!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals(ERROR, validator.validate(agreementDTO).orElseThrow().getErrorCode());
        assertEquals("Field country is empty!", validator.validate(agreementDTO).orElseThrow().getDescription());
    }

    @Test
    void shouldReturnError_whenGetNullCountry() {
        when(agreementDTO.getCountry()).thenReturn(null);
        when(errorFactory.buildError(ERROR))
                .thenReturn(new ValidationErrorDTO(ERROR, "Field country is empty!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals(ERROR, validator.validate(agreementDTO).orElseThrow().getErrorCode());
        assertEquals("Field country is empty!", validator.validate(agreementDTO).orElseThrow().getDescription());
    }

    @Test
    void shouldReturnEmptyOptional_whenGetCountryAndTwoRisksWithMedical() {
        when(agreementDTO.getCountry()).thenReturn("Latvia");

        assertTrue(validator.validate(agreementDTO).isEmpty());
    }
}