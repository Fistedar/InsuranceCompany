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
class EmptyPersonCodeAgreementValidationFieldTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private AgreementDTO agreement;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private EmptyPersonCodeAgreementValidationField validator;

    @Test
    void shouldReturnOptionalEmpty_whenGetPersonCode() {
        when(personDTO.getPersonCode()).thenReturn("12341");

        assertTrue(validator.validate(agreement, personDTO).isEmpty());
    }

    @Test
    void shouldReturnError_whenGetEmptyPersonCode() {
        when(personDTO.getPersonCode()).thenReturn("");
        when(errorFactory.buildError("ERROR_CODE_17")).thenReturn(
                new ValidationErrorDTO("ERROR_CODE_17", "Field personCode is empty!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_17", validator.validate(agreement, personDTO).get().getErrorCode());
        assertEquals("Field personCode is empty!", validator.validate(agreement, personDTO).get().getDescription());
    }

    @Test
    void shouldReturnError_whenGetNullPersonCode() {
        when(personDTO.getPersonCode()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_17")).thenReturn(
                new ValidationErrorDTO("ERROR_CODE_17", "Field personCode is empty!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_17", validator.validate(agreement, personDTO).get().getErrorCode());
        assertEquals("Field personCode is empty!", validator.validate(agreement, personDTO).get().getDescription());
    }

}