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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyMedicalRiskLimitLevelAgreementValidationTest {

    private static final String ERROR_CODE = "ERROR_CODE_14";
    private static final String ERROR_DESCRIPTION = "MedicalRiskLimitLevel is empty!";

    @Mock
    private PersonDTO person;
    @Mock
    private AgreementDTO agreement;
    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyMedicalRiskLimitLevelAgreementValidation validator;

    @Test
    void shouldEmptyOptional_whenHasMedicalRiskLimitLevel_andMedicalRiskLimitLevelEnabled() {
        ReflectionTestUtils.setField(validator, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        when(person.getMedicalRiskLimitLevel()).thenReturn("LEVEL_1");

        assertTrue(validator.validate(agreement, person).isEmpty());
    }

    @Test
    void shouldError_whenMedicalRiskLimitLevelIsEmpty_andMedicalRiskLimitLevelEnabled() {
        ReflectionTestUtils.setField(validator, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        when(person.getMedicalRiskLimitLevel()).thenReturn(" ");
        when(errorFactory.buildError(ERROR_CODE)).thenReturn(new ValidationErrorDTO(ERROR_CODE, ERROR_DESCRIPTION));

        assertTrue(validator.validate(agreement, person).isPresent());
        assertEquals(ERROR_CODE, validator.validate(agreement, person).orElseThrow().getErrorCode());
        assertEquals(ERROR_DESCRIPTION, validator.validate(agreement, person).orElseThrow().getDescription());
    }

    @Test
    void shouldEmptyOptional_whenMedicalRiskLimitLevelNull_andMedicalRiskLimitLevelEnabled() {
        ReflectionTestUtils.setField(validator, "medicalRiskLimitLevelEnabled", Boolean.FALSE);
        when(person.getMedicalRiskLimitLevel()).thenReturn(null);

        assertTrue(validator.validate(agreement, person).isEmpty());
    }

}