package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.eq;

@ExtendWith(MockitoExtension.class)
class SelectedRiskValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private SelectedRiskValidation selectedRiskValidation;

    @Test
    void shouldNotValidateWhenSelectedRisksIsNull() {
        when(request.getSelectedRisks()).thenReturn(null);
        assertTrue(selectedRiskValidation.validate(request).isEmpty());
        verifyNoInteractions(classifierValueRepository, validationErrorFactory);
    }

    @Test
    void shouldValidateWithoutErrors() {
        when(request.getSelectedRisks()).thenReturn(List.of("Ic-RiskType1", "Ic-RiskType2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "Ic-RiskType1"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "Ic-RiskType2"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));

        assertTrue(selectedRiskValidation.validate(request).isEmpty());
    }

    @Test
    void shouldValidateWithErrors() {
        when(request.getSelectedRisks()).thenReturn(List.of("Ic-RiskType1", "Ic-RiskType2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "Ic-RiskType1"))
                .thenReturn(Optional.empty());
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "Ic-RiskType2"))
                .thenReturn(Optional.empty());
        when(validationErrorFactory.buildError(eq("ERROR_CODE_9"), any(Placeholder.class)))
                .thenReturn(mock(ValidationError.class));

        List<ValidationError> errors = selectedRiskValidation.validate(request);
        assertEquals(2, errors.size());
    }

}