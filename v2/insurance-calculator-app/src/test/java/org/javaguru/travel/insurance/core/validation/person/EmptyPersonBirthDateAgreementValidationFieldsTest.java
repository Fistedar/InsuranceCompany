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
class EmptyPersonBirthDateAgreementValidationFieldsTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private AgreementDTO agreement;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private EmptyPersonBirthDateAgreementValidationFields validator;

    @Test
    void shouldReturnEmptyOptional_whenGetValidBirthDate() {
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2000, 10, 10));

        assertTrue(validator.validate(agreement, personDTO).isEmpty());
    }

    @Test
    void shouldReturnError_whenGetEmptyBirthDate() {
        when(personDTO.getPersonBirthDate()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_13"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_13", "PersonBirthDate is empty!"));

        assertTrue(validator.validate(agreement, personDTO).isPresent());
        assertEquals("ERROR_CODE_13", validator.validate(agreement, personDTO).orElseThrow().getErrorCode());
        assertEquals("PersonBirthDate is empty!", validator.validate(agreement, personDTO).orElseThrow().getDescription());
    }
}