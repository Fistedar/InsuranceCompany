package org.javaguru.travel.insurance.core.validation.internal;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelAgreementUuidValidationImplTest {


    @Mock
    private AgreementRepository repository;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private TravelAgreementUuidValidationImpl validator;

    @Test
    @DisplayName("Test: Should return error, when uuid is empty")
    void shouldReturnError_whenUuidIsEmpty() {
        String uuid = " ";
        when(errorFactory.buildError("ERROR_CODE_18"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_18", "Field uuid is empty!"));

        assertFalse(validator.validate(uuid).isEmpty());
        assertEquals("ERROR_CODE_18", validator.validate(uuid).getFirst().getErrorCode());
        assertEquals("Field uuid is empty!", validator.validate(uuid).getFirst().getDescription());
    }

    @Test
    @DisplayName("Test: Should return error, when uuid is null")
    void shouldReturnError_whenUuidIsNull() {
        when(errorFactory.buildError("ERROR_CODE_18"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_18", "Field uuid is empty!"));

        assertFalse(validator.validate(null).isEmpty());
        assertEquals("ERROR_CODE_18", validator.validate(null).getFirst().getErrorCode());
        assertEquals("Field uuid is empty!", validator.validate(null).getFirst().getDescription());
    }

    @Test
    @DisplayName("Test: Should return error, when uuid isn't exist")
    void shouldReturnError_whenUuidIsNotExist() {
        when(repository.findByUuid("POW-POW")).thenReturn(Optional.empty());
        when(errorFactory.buildError(eq("ERROR_CODE_19"), any(Placeholder.class)))
                .thenReturn(mock(ValidationErrorDTO.class));

        assertFalse(validator.validate("POW-POW").isEmpty());
    }

    @Test
    @DisplayName("Test: Should return empty list error, when uuid exist")
    void shouldReturnError_whenUuidExist() {
        when(repository.findByUuid("Pif-paf")).thenReturn(Optional.of(new AgreementEntity()));

        assertTrue(validator.validate("Pif-paf").isEmpty());
    }
}