package org.fistedar.black.list.core.validation;

import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonCodeValidatorTest {

    @Mock
    private PersonDTO person;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private PersonCodeValidator personCodeValidator;

    private final String ERROR_CODE = "ERROR_CODE_3";

    @Test
    void shouldGetEmptyOptional_whenPersonCodeIsNotNullOrEmpty() {
        when(person.getPersonCode()).thenReturn("123");

        assertTrue(personCodeValidator.validate(person).isEmpty());
    }

    @Test
    void shouldGetError_whenPersonCodeIsNull() {
        when(person.getPersonCode()).thenReturn(null);
        when(errorFactory.buildError(ERROR_CODE))
                .thenReturn(new ValidationErrorDTO(ERROR_CODE, "Field personCode must not be empty!"));

        assertEquals(personCodeValidator.validate(person).orElseThrow().getErrorCode(), ERROR_CODE);
        assertEquals(personCodeValidator.validate(person).orElseThrow().getDescription(), "Field personCode must not be empty!");
    }

    @Test
    void shouldGetError_whenPersonCodeIsEmpty() {
        when(person.getPersonCode()).thenReturn(" ");
        when(errorFactory.buildError(ERROR_CODE))
                .thenReturn(new ValidationErrorDTO(ERROR_CODE, "Field personCode must not be empty!"));

        assertEquals(personCodeValidator.validate(person).orElseThrow().getErrorCode(), ERROR_CODE);
        assertEquals(personCodeValidator.validate(person).orElseThrow().getDescription(), "Field personCode must not be empty!");
    }
}