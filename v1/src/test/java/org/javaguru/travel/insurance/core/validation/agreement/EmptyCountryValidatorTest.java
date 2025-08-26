package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryValidatorTest {
    private static final String ERROR = "ERROR_CODE_10";

    @Mock
    TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyCountryValidator validator;

    @Test
    void shouldReturnError_whenGetEmptyCountry() {
        when(request.getCountry()).thenReturn(" ");
        when(errorFactory.buildError(ERROR))
                .thenReturn(new ValidationError(ERROR, "Field country is empty!"));

        assertEquals(ERROR, validator.validate(request).orElseThrow().message());
        assertEquals("Field country is empty!", validator.validate(request).orElseThrow().description());
    }

    @Test
    void shouldReturnError_whenGetNullCountry() {
        when(request.getCountry()).thenReturn(null);
        when(errorFactory.buildError(ERROR))
                .thenReturn(new ValidationError(ERROR, "Field country is empty!"));

        assertEquals(ERROR, validator.validate(request).orElseThrow().message());
        assertEquals("Field country is empty!", validator.validate(request).orElseThrow().description());
    }

    @Test
    void shouldReturnEmptyOptional_whenGetCountryAndTwoRisksWithMedical() {
        when(request.getCountry()).thenReturn("Latvia");

        assertTrue(validator.validate(request).isEmpty());
    }
}