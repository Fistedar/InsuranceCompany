package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExistsCountryAgreementValidationFieldsTest {

    private static final String COUNTRY = "COUNTRY";
    private static final String ERROR_CODE = "ERROR_CODE_11";
    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private ValidationErrorFactory errorFactory;
    @Mock
    private ClassifierValueRepository classifierValueRepository;
    @InjectMocks
    private ExistsCountryAgreementValidationFields validator;

    @Test
    void shouldReturnError_whenGetUnsupportedCountry() {
        when(agreementDTO.getCountry()).thenReturn("JAVA");
        when(classifierValueRepository.findByClassifierTitleAndIc(COUNTRY, "JAVA"))
                .thenReturn(Optional.empty());
        when(errorFactory.buildError(eq(ERROR_CODE), any(Placeholder.class)))
                .thenReturn(new ValidationErrorDTO(ERROR_CODE, "Country ic = JAVA not supported!"));

        assertEquals(ERROR_CODE, validator.validate(agreementDTO).orElseThrow().getErrorCode());
        assertEquals("Country ic = JAVA not supported!", validator.validate(agreementDTO).orElseThrow().getDescription());
    }

    @Test
    void shouldReturnEmptyOptional_whenGetSupportedCountry() {
        when(agreementDTO.getCountry()).thenReturn("Latvia");
        when(classifierValueRepository.findByClassifierTitleAndIc(COUNTRY, "Latvia"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));

        assertTrue(validator.validate(agreementDTO).isEmpty());
    }
}