package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
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
class FirstNameAgreementValidationFieldsTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private AgreementDTO agreement;
    @Mock
    private ValidationErrorFactory validationErrorFactory;
    @InjectMocks
    private FirstNameAgreementValidationFields validator;

    @Test
    void validateFirstNameIsNull() {
        when(personDTO.getPersonFirstName()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_1", "Field personFirstName is empty!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_1", validator.validate(agreement, personDTO).orElseThrow().getErrorCode());
        assertEquals("Field personFirstName is empty!", validator.validate(agreement, personDTO).orElseThrow().getDescription());
    }

    @Test
    void validateFirstNameIsEmpty() {
        when(personDTO.getPersonFirstName()).thenReturn(" ");
        when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_1", "Field personFirstName is empty!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_1", validator.validate(agreement, personDTO).orElseThrow().getErrorCode());
        assertEquals("Field personFirstName is empty!", validator.validate(agreement, personDTO).orElseThrow().getDescription());
    }

    @Test
    void validateHasFirstName() {
        when(personDTO.getPersonFirstName()).thenReturn("Gaby");

        assertTrue(validator.validate(agreement, personDTO).isEmpty());
    }
}