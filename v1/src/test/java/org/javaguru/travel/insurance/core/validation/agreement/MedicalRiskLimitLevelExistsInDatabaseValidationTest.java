package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
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
class MedicalRiskLimitLevelExistsInDatabaseValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory errorFactory;

    @Mock
    private MedicalRiskLimitLevelRepository repository;

    @InjectMocks
    private MedicalRiskLimitLevelExistsInDatabaseValidation validator;

    @Test
    void shouldEmptyOptional_whenMedicalRiskLimitLevelExistsInDatabase() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_1");
        when(repository.findByMedicalRiskLimit("LEVEL_1"))
                .thenReturn(Optional.of(mock(MedicalRiskLimitLevel.class)));

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldError_whenMedicalRiskLimitLevelNotExistsInDatabase() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_2");
        when(repository.findByMedicalRiskLimit("LEVEL_2"))
                .thenReturn(Optional.empty());
        when(errorFactory.buildError(eq("ERROR_CODE_15"),any(Placeholder.class)))
                .thenReturn(mock(ValidationError.class));

        assertTrue(validator.validate(request).isPresent());
    }
}