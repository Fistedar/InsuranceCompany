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
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PastPersonBirthDateValidatorTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private AgreementDTO agreement;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private PastPersonBirthDateAgreementValidationFields validator;

    @Test
    void shouldReturnEmptyOption_whenGetBirthDateInPaste() {
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2010, 2, 3));
        assertTrue(validator.validate(agreement, personDTO).isEmpty());
    }

    @Test
    void shouldReturnError_whenGetBirthDateInFuture() {
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2030, 2, 4));
        when(errorFactory.buildError("ERROR_CODE_12")).thenReturn(new ValidationErrorDTO("ERROR_CODE_12", "PersonBirthDate can't be in future!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_12", validator.validate(agreement, personDTO).orElseThrow().getErrorCode());
        assertEquals("PersonBirthDate can't be in future!", validator.validate(agreement, personDTO).orElseThrow().getDescription());
    }
}