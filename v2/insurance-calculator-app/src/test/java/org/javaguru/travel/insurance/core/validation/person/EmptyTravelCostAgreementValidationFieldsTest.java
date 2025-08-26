package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyTravelCostAgreementValidationFieldsTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @InjectMocks
    private EmptyTravelCostAgreementValidationFields validator;

    @Test
    void shouldGetEmptyOptional_whenTravelCostEnabled_andRisksWithTravelCostIsValid() {
        ReflectionTestUtils.setField(validator, "travelCostEnabled", Boolean.TRUE);
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("TRAVEL_CANCELLATION"));
        when(personDTO.getTravelCost()).thenReturn(BigDecimal.TEN);

        assertTrue(validator.validate(agreementDTO, personDTO).isEmpty());
    }

    @Test
    void shouldGetEmptyOptional_whenTravelCostDisabled_andRisksWithTravelCostIsInvalid() {
        ReflectionTestUtils.setField(validator, "travelCostEnabled", Boolean.FALSE);

        assertTrue(validator.validate(agreementDTO, personDTO).isEmpty());
    }

    @Test
    void shouldGetEmptyOptional_whenTravelCostEnabled_andRisksWithTravelCostIsInvalid() {
        ReflectionTestUtils.setField(validator, "travelCostEnabled", Boolean.TRUE);
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICATION"));

        assertTrue(validator.validate(agreementDTO, personDTO).isEmpty());
    }

    @Test
    void shouldGetError_whenTravelCostEnabled_andRiskValidButTravelCostIsInvalid() {
        ReflectionTestUtils.setField(validator, "travelCostEnabled", Boolean.TRUE);
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("TRAVEL_CANCELLATION"));
        when(personDTO.getTravelCost()).thenReturn(BigDecimal.ZERO);
        when(errorFactory.buildError("ERROR_CODE_20"))
                .thenReturn(new ValidationErrorDTO("ERROR_CODE_20","Field travelCost is invalid!"));

        assertTrue(validator.validate(agreementDTO, personDTO).isPresent());
        assertEquals("ERROR_CODE_20",validator.validate(agreementDTO,personDTO).get().getErrorCode());
        assertEquals("Field travelCost is invalid!",validator.validate(agreementDTO,personDTO).get().getDescription());
    }
}