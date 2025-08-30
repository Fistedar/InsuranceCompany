package org.fistedar.black.list.core.validation;

import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonFirstNameValidatorTest {

    @Mock
    private PersonDTO person;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private PersonFirstNameValidator validator;

    private final String ERROR_CODE = "ERROR_CODE_1";

    @Test
    void shouldGetOptionalEmpty_whenPersonFirstNameIsNotNullOrEmpty() {
        when(person.getPersonFirstName()).thenReturn("Petya");

        assertTrue(validator.validate(person).isEmpty());
    }

    @Test
    void shouldGetError_whenPersonFirstNameIsNull() {
        when(person.getPersonFirstName()).thenReturn(null);
        when(errorFactory.buildError(ERROR_CODE))
                .thenReturn(new ValidationErrorDTO(ERROR_CODE,"Field personFirstName must not be empty!"));

        assertEquals(validator.validate(person).orElseThrow().getErrorCode(), ERROR_CODE);
        assertEquals(validator.validate(person).orElseThrow().getDescription(), "Field personFirstName must not be empty!");
    }

    @Test
    void shouldGetError_whenPersonFirstNameIsEmpty() {
        when(person.getPersonFirstName()).thenReturn(" ");
        when(errorFactory.buildError(ERROR_CODE))
                .thenReturn(new ValidationErrorDTO(ERROR_CODE,"Field personFirstName must not be empty!"));

        assertEquals(validator.validate(person).orElseThrow().getErrorCode(), ERROR_CODE);
        assertEquals(validator.validate(person).orElseThrow().getDescription(), "Field personFirstName must not be empty!");
    }
}