package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AvailableSelectedRiskValidatorTest {

    private final TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AvailableSelectedRiskValidator riskValidator;

    @Test
    void validateListRisksIsEmpty() {
        request.setSelectedRisks(null);

        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_8"))
                .thenReturn(new ValidationError("ERROR_CODE_8", "Field RiskType is empty!"));

        assertEquals("Field RiskType is empty!", riskValidator.validate(request).orElseThrow().description());
        assertEquals("ERROR_CODE_8", riskValidator.validate(request).orElseThrow().message());
    }

    @Test
    void validateListRisksHaveOneRiskType() {
        request.setSelectedRisks(List.of("RiskType"));
        assertTrue(riskValidator.validate(request).isEmpty());
    }
}