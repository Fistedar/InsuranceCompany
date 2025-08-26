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
class PersonFirstNameFormatValidationTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private PersonFirstNameFormatValidation personFirstNameFormatValidation;

    @Test
    @DisplayName("Test: Valid format firstName")
    void shouldReturnOptionalEmpty_whenValidFormatFirstName() {
        when(personDTO.getPersonFirstName()).thenReturn("Jake");

        assertTrue(personFirstNameFormatValidation.validate(agreementDTO, personDTO).isEmpty());
    }

    @Test
    @DisplayName("Test: Invalid format firstName")
    void shouldReturnError_whenInvalidFormatFirstName() {
        when(personDTO.getPersonFirstName()).thenReturn("Айдар");
        when(errorFactory.buildError(eq("ERROR_CODE_22"), any(Placeholder.class)))
                .thenReturn(new ValidationErrorDTO());

        assertTrue(personFirstNameFormatValidation.validate(agreementDTO, personDTO).isPresent());
    }
}