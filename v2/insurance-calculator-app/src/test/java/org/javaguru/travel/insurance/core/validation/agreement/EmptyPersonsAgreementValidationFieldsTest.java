package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyPersonsAgreementValidationFieldsTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private ValidationErrorFactory validationErrorFactory;
    @InjectMocks
    private EmptyPersonsAgreementValidationFields validator;

    @Test
    void shouldTakeOptionalEmpty_whenHavePersons() {
        when(agreementDTO.getPersons()).thenReturn(List.of(personDTO));

        assertTrue(validator.validate(agreementDTO).isEmpty());
    }

    @Test
    void shouldTakeError_whenPersonsIsNull() {
        when(agreementDTO.getPersons()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_16"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_16","Persons is empty!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals(validator.validate(agreementDTO).get().getErrorCode(),"ERROR_CODE_16");
        assertEquals(validator.validate(agreementDTO).get().getDescription(),"Persons is empty!");
    }

    @Test
    void shouldTakeError_whenPersonsIsEmpty() {
        when(agreementDTO.getPersons()).thenReturn(List.of());
        when(validationErrorFactory.buildError("ERROR_CODE_16"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_16","Persons is empty!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals(validator.validate(agreementDTO).get().getErrorCode(),"ERROR_CODE_16");
        assertEquals(validator.validate(agreementDTO).get().getDescription(),"Persons is empty!");
    }
}