package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
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
class PastPersonBirthDateAgreementValidationFieldsTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private PastPersonBirthDateAgreementValidationFields validator;

    @Test
    @DisplayName("Test: Valid data")
    void shouldReturnOptionalEmpty_whenGetValidData() {
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2000, 5, 5));

        assertTrue(validator.validate(agreementDTO, personDTO).isEmpty());
    }

    @Test
    @DisplayName("Test: BirthData is past")
    void shouldReturnError_whenBirthDataIsPast() {
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2100, 2, 20));
        when(errorFactory.buildError("ERROR_CODE_12"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_12", "PersonBirthDate in future!"));

        assertTrue(validator.validate(agreementDTO, personDTO).isPresent());
        assertEquals("ERROR_CODE_12", validator.validate(agreementDTO, personDTO).get().getErrorCode());
        assertEquals("PersonBirthDate in future!", validator.validate(agreementDTO, personDTO).get().getDescription());
    }
}