package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationErrorFactoryTest {

    @Mock
    Environment environment;

    @InjectMocks
    ValidationErrorFactory validationErrorFactory;

    @Test
    void buildError_withErrorCode_shouldReturnValidationError() {
        String errorCode = "ERROR_CODE_8";
        String expectedMessage = "Field RiskType is empty!";
        when(environment.getProperty(errorCode)).thenReturn(expectedMessage);

        ValidationError result = validationErrorFactory.buildError(errorCode);

        assertNotNull(result);
        assertEquals(errorCode, result.message());
        assertEquals(expectedMessage, result.description());
        verify(environment).getProperty(errorCode);
    }

    @Test
    void buildError_withPlaceholder_shouldReturnValidationError() {
        String errorCode = "ERROR_CODE_9";
        String expectedMessage = "Risk Type ic = TRAVEL_ANIMAL not supported!";
        Placeholder placeholder = new Placeholder("NOT_EXISTING_RISK_TYPE", "TRAVEL_ANIMAL");
        when(environment.getProperty(errorCode)).thenReturn(expectedMessage);

        ValidationError result = validationErrorFactory.buildError(errorCode, placeholder);

        assertNotNull(result);
        assertEquals(errorCode, result.message());
        assertEquals(expectedMessage, result.description());
        verify(environment).getProperty(errorCode);
    }
}