package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FirstNameValidatorTest {

    private final TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private FirstNameValidator validator;

    @Test
    void validateFirstNameIsNull() {
        request.setPersonFirstName(null);
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationError("ERROR_CODE_1", "Field personFirstName is empty!"));

        assertEquals("ERROR_CODE_1", validator.validate(request).orElseThrow().message());
        assertEquals("Field personFirstName is empty!", validator.validate(request).orElseThrow().description());
    }

    @Test
    void validateFirstNameIsEmpty() {
        request.setPersonFirstName("");
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationError("ERROR_CODE_1", "Field personFirstName is empty!"));

        assertEquals("ERROR_CODE_1", validator.validate(request).orElseThrow().message());
        assertEquals("Field personFirstName is empty!", validator.validate(request).orElseThrow().description());
    }

    @Test
    void validateHasFirstName() {
        request.setPersonFirstName("Guru");
        assertTrue(validator.validate(request).isEmpty());
    }
}