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
class PersonLastNameFormatValidationTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private PersonLastNameFormatValidation personLastNameFormatValidation;

    @Test
    @DisplayName("Test: Valid format lastName")
    void shouldReturnOptionalEmpty_whenValidFormatFirstName() {
        when(personDTO.getPersonLastName()).thenReturn("Smith");

        assertTrue(personLastNameFormatValidation.validate(agreementDTO, personDTO).isEmpty());
    }

    @Test
    @DisplayName("Test: Invalid format lastName")
    void shouldReturnError_whenInvalidFormatFirstName() {
        when(personDTO.getPersonLastName()).thenReturn("Галиев");
        when(errorFactory.buildError(eq("ERROR_CODE_23"), any(Placeholder.class)))
                .thenReturn(new ValidationErrorDTO());

        assertTrue(personLastNameFormatValidation.validate(agreementDTO, personDTO).isPresent());
    }
}