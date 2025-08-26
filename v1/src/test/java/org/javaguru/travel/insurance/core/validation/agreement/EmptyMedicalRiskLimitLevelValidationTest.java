package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EmptyMedicalRiskLimitLevelValidationTest {

    private static final String ERROR_CODE = "ERROR_CODE_14";
    private static final String ERROR_DESCRIPTION = "MedicalRiskLimitLevel is empty!";

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyMedicalRiskLimitLevelValidation validator;

    @Test
    void shouldEmptyOptional_whenHasMedicalRiskLimitLevel() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_1");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldError_whenMedicalRiskLimitLevelIsEmpty() {
        when(request.getMedicalRiskLimitLevel()).thenReturn(" ");
        when(errorFactory.buildError(ERROR_CODE)).thenReturn(new ValidationError(ERROR_CODE, ERROR_DESCRIPTION));

        assertEquals(ERROR_CODE, validator.validate(request).orElseThrow().message());
        assertEquals(ERROR_DESCRIPTION, validator.validate(request).orElseThrow().description());
    }

}