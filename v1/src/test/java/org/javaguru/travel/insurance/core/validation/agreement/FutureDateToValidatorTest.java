package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FutureDateToValidatorTest {
    private final TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();

    @Mock
    private DateTimeUtil dateTimeUtil = new DateTimeUtil();

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private FutureDateToValidator futureDateToValidator;

    @Test
    void validateValid() {
        request.setAgreementDateFrom(LocalDate.of(2026, 2, 5));
        request.setAgreementDateTo(LocalDate.of(2026, 2, 7));
        Mockito.when(dateTimeUtil.getCurrentDate()).thenReturn(LocalDate.now());
        assertTrue(futureDateToValidator.validate(request).isEmpty());
    }

    @Test
    void validateInvalid() {
        request.setAgreementDateFrom(LocalDate.of(2024, 2, 5));
        request.setAgreementDateTo(LocalDate.of(2024, 2, 7));

        Mockito.when(dateTimeUtil.getCurrentDate()).thenReturn(LocalDate.now());
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_6"))
                .thenReturn(new ValidationError("ERROR_CODE_6", "DateTo from the past!"));

        assertEquals("ERROR_CODE_6", futureDateToValidator.validate(request).orElseThrow().message());
        assertEquals("DateTo from the past!", futureDateToValidator.validate(request).orElseThrow().description());
    }
}