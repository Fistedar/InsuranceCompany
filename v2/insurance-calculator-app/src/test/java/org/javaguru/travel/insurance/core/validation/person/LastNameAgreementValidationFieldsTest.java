package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LastNameAgreementValidationFieldsTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private AgreementDTO agreement;
    @Mock
    private ValidationErrorFactory validationErrorFactory;
    @InjectMocks
    private LastNameAgreementValidationFields validator;

    @Test
    void validateFirstNameIsNull() {
        when(personDTO.getPersonLastName()).thenReturn(null);
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_2"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_2", "Field personLastName is empty!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_2", validator.validate(agreement, personDTO).orElseThrow().getErrorCode());
        assertEquals("Field personLastName is empty!", validator.validate(agreement, personDTO).orElseThrow().getDescription());
    }

    @Test
    void validateFirstNameIsEmpty() {
        when(personDTO.getPersonLastName()).thenReturn(" ");
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_2"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_2", "Field personLastName is empty!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_2", validator.validate(agreement, personDTO).orElseThrow().getErrorCode());
        assertEquals("Field personLastName is empty!", validator.validate(agreement, personDTO).orElseThrow().getDescription());
    }

    @Test
    void validateHasFirstName() {
        when(personDTO.getPersonLastName()).thenReturn("John");

        assertNotNull(validator.validate(agreement, personDTO));
    }
}