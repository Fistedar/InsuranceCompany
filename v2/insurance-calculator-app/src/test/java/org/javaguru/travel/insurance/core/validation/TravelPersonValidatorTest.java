package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPersonValidatorTest {

    @Mock
    private AgreementDTO agreement;
    @Mock
    private PersonDTO person;
    @Mock
    private TravelPersonValidationFields validationFields;
    @InjectMocks
    private TravelPersonValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TravelPersonValidator(List.of(validationFields));
    }

    @Test
    void shouldNotReturnErrors_whenValidatorsIsEmpty() {
        when(agreement.getPersons()).thenReturn(List.of(person));
        when(validationFields.validate(agreement, person)).thenReturn(Optional.empty());
        when(validationFields.validateList(agreement, person)).thenReturn(List.of());

        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnErrors_whenSingleValidatorsIsEmpty_andMultipleValidatorsNotEmpty() {
        when(agreement.getPersons()).thenReturn(List.of(person));
        when(validationFields.validate(agreement, person)).thenReturn(Optional.empty());
        when(validationFields.validateList(agreement, person))
                .thenReturn(List.of(new ValidationErrorDTO("Test", "Test"),
                        new ValidationErrorDTO("Test2", "Test2")));

        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(2, errors.size());
    }

    @Test
    void shouldReturnErrors_whenMultiplyValidatorsIsEmpty_andSingleValidatorsNotEmpty() {
        when(agreement.getPersons()).thenReturn(List.of(person));
        when(validationFields.validate(agreement, person)).thenReturn(Optional.of(new ValidationErrorDTO("Test", "Test")));
        when(validationFields.validateList(agreement, person)).thenReturn(List.of());

        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(1, errors.size());
    }

}