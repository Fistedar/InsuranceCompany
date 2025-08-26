package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonCodeFormatValidationTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private PersonCodeFormatValidation personCodeFormatValidation;

    @Test
    @DisplayName("Test: Valid person code")
    void shouldReturnOptionalEmpty_whenValidPersonCode() {
        when(personDTO.getPersonCode()).thenReturn("123456-12345");

        assertTrue(personCodeFormatValidation.validate(agreementDTO, personDTO).isEmpty());
    }

    @Test
    @DisplayName("Test: Invalid person code")
    void shouldReturnError_whenInvalidPersonCode() {
        when(personDTO.getPersonCode()).thenReturn("12345-123456");
        when(errorFactory.buildError(eq("ERROR_CODE_21"), any(Placeholder.class)))
                .thenReturn(new ValidationErrorDTO());

        assertTrue(personCodeFormatValidation.validate(agreementDTO, personDTO).isPresent());

    }
}