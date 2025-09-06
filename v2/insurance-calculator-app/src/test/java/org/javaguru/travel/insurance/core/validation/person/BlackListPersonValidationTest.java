package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.blacklist.BlackListChecker;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlackListPersonValidationTest {
    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private BlackListChecker blackListChecker;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private BlackListPersonValidation blackListPersonValidation;

    @Test
    void shouldGetError_thenPersonOnBlacklist() {
        when(personDTO.getPersonFirstName()).thenReturn("Test");
        when(personDTO.getPersonLastName()).thenReturn("Test");
        when(personDTO.getPersonCode()).thenReturn("Test");

        when(blackListChecker.checkBlackList(personDTO)).thenReturn(true);
        when(errorFactory.buildError(eq("ERROR_CODE_24"), any(Placeholder.class)))
                .thenReturn(mock(ValidationErrorDTO.class));

        Optional<ValidationErrorDTO> errorDTO = blackListPersonValidation.validate(agreementDTO, personDTO);

        assertTrue(errorDTO.isPresent());
    }

    @Test
    void shouldGetOptionalEmpty_whenPersonFirstNameIsEmpty() {
        when(personDTO.getPersonFirstName()).thenReturn("");

        Optional<ValidationErrorDTO> error = blackListPersonValidation.validate(agreementDTO, personDTO);

        assertTrue(error.isEmpty());
    }

    @Test
    void shouldGetOptionalEmpty_whenPersonLastNameIsEmpty() {
        when(personDTO.getPersonFirstName()).thenReturn("Test");
        when(personDTO.getPersonLastName()).thenReturn("");

        Optional<ValidationErrorDTO> error = blackListPersonValidation.validate(agreementDTO, personDTO);

        assertTrue(error.isEmpty());
    }

    @Test
    void shouldGetOptionalEmpty_whenPersonCodeIsEmpty() {
        when(personDTO.getPersonFirstName()).thenReturn("Test");
        when(personDTO.getPersonLastName()).thenReturn("Test");
        when(personDTO.getPersonCode()).thenReturn("");

        Optional<ValidationErrorDTO> error = blackListPersonValidation.validate(agreementDTO, personDTO);

        assertTrue(error.isEmpty());
    }

    @Test
    void shouldGetOptionalEmpty_whenPersonCodeIsNull() {
        when(personDTO.getPersonFirstName()).thenReturn("Test");
        when(personDTO.getPersonLastName()).thenReturn("Test");
        when(personDTO.getPersonCode()).thenReturn("Test");

        when(blackListChecker.checkBlackList(personDTO)).thenReturn(false);

        Optional<ValidationErrorDTO> error = blackListPersonValidation.validate(agreementDTO, personDTO);

        assertTrue(error.isEmpty());
    }
}