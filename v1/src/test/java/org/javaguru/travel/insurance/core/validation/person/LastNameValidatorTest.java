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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class LastNameValidatorTest {
    private final TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private LastNameValidator validator;

    @Test
    void validateFirstNameIsNull() {
        request.setPersonLastName(null);
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_2"))
                .thenReturn(new ValidationError("ERROR_CODE_2", "Field personLastName is empty!"));

        assertEquals("ERROR_CODE_2", validator.validate(request).orElseThrow().message());
        assertEquals("Field personLastName is empty!", validator.validate(request).orElseThrow().description());
    }

    @Test
    void validateFirstNameIsEmpty() {
        request.setPersonLastName("");
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_2"))
                .thenReturn(new ValidationError("ERROR_CODE_2", "Field personLastName is empty!"));

        assertEquals("ERROR_CODE_2", validator.validate(request).orElseThrow().message());
        assertEquals("Field personLastName is empty!", validator.validate(request).orElseThrow().description());
    }

    @Test
    void validateHasFirstName() {
        request.setPersonLastName("Guru");
        assertNotNull(validator.validate(request));
    }
}