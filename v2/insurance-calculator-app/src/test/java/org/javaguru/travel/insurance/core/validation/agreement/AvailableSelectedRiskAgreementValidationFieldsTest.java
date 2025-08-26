package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailableSelectedRiskAgreementValidationFieldsTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private AvailableSelectedRiskAgreementValidationFields validator;

    @Test
    @DisplayName("Test: Get empty optional")
    void shouldGetEmptyOptional_whenGetValidData() {
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("Risk"));

        assertTrue(validator.validate(agreementDTO).isEmpty());
    }

    @Test
    @DisplayName("Test: Get optional with error ")
    void shouldGetOptionalWithError_whenGetEmptyList() {
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of());
        when(errorFactory.buildError("ERROR_CODE_8"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_8","Field RiskType is empty!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals(validator.validate(agreementDTO).get().getErrorCode(),"ERROR_CODE_8");
        assertEquals(validator.validate(agreementDTO).get().getDescription(),"Field RiskType is empty!");
    }

    @Test
    @DisplayName("Test: Get optional with error")
    void shouldGetOptionalWithError_whenGetNullList() {
        when(agreementDTO.getSelectedRisks()).thenReturn(null);
        when(errorFactory.buildError("ERROR_CODE_8"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_8","Field RiskType is empty!"));

        assertTrue(validator.validate(agreementDTO).isPresent());
        assertEquals(validator.validate(agreementDTO).get().getErrorCode(),"ERROR_CODE_8");
        assertEquals(validator.validate(agreementDTO).get().getDescription(),"Field RiskType is empty!");
    }
}