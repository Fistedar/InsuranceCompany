package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
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
class PastPersonBirthDateValidatorTest {
    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PastPersonBirthDateValidator validator;

    @Test
    void shouldReturnEmptyOption_whenGetBirthDateInPaste() {
        when(request.getPersonBirthDate()).thenReturn(LocalDate.of(2010, 2, 3));
        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldReturnError_whenGetBirthDateInFuture() {
        when(request.getPersonBirthDate()).thenReturn(LocalDate.of(2030, 2, 4));
        when(errorFactory.buildError("ERROR_CODE_12")).thenReturn(new ValidationError("ERROR_CODE_12", "PersonBirthDate can't be in future!"));

        assertEquals("ERROR_CODE_12", validator.validate(request).orElseThrow().message());
        assertEquals("PersonBirthDate can't be in future!", validator.validate(request).orElseThrow().description());
    }
}