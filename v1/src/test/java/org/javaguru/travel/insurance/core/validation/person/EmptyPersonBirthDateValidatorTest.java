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
class EmptyPersonBirthDateValidatorTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyPersonBirthDateValidator validator;

    @Test
    void shouldReturnEmptyOptional_whenGetValidBirthDate() {
        when(request.getPersonBirthDate()).thenReturn(LocalDate.of(2000, 10, 10));

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldReturnError_whenGetEmptyBirthDate() {
        when(request.getPersonBirthDate()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_13"))
                .thenReturn(new ValidationError("ERROR_CODE_13", "PersonBirthDate is empty!"));

        assertEquals("ERROR_CODE_13", validator.validate(request).orElseThrow().message());
        assertEquals("PersonBirthDate is empty!", validator.validate(request).orElseThrow().description());
    }
}