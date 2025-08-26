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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DateFromBeforeDateToValidatorTest {
    private final TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private DateFromBeforeDateToValidator validator;


    @Test
    void dateIsInvalid() {
        request.setAgreementDateTo(LocalDate.of(2025, 10, 10));
        request.setAgreementDateFrom(LocalDate.of(2025, 10, 15));
        Mockito.when(validationErrorFactory.buildError("ERROR_CODE_7"))
                .thenReturn(new ValidationError("ERROR_CODE_7", "DateTo before DateFrom!"));

        assertEquals("ERROR_CODE_7", validator.validate(request).orElseThrow().message());
        assertEquals("DateTo before DateFrom!", validator.validate(request).orElseThrow().description());
    }

    @Test
    void dateIsValid() {
        request.setAgreementDateTo(LocalDate.of(2025, 10, 15));
        request.setAgreementDateFrom(LocalDate.of(2025, 10, 10));

        assertTrue(validator.validate(request).isEmpty());
    }
}