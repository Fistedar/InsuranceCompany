package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
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
class DateToValidatorTest {
    private final TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();

    @Mock
    private ValidationErrorFactory messageService;

    @InjectMocks
    private DateToValidator validator;

    @Test
    void validateDateFromIsValid() {
        request.setAgreementDateTo(LocalDate.now());
        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    @DisplayName("")
    void validateDateFromIsNull() {
        request.setAgreementDateFrom(null);
        Mockito.when(messageService.buildError("ERROR_CODE_4"))
                .thenReturn(new ValidationError("ERROR_CODE_4", "Field agreementDateTo is empty!"));
        assertEquals("ERROR_CODE_4", validator.validate(request).orElseThrow().message());
        assertEquals("Field agreementDateTo is empty!", validator.validate(request).orElseThrow().description());
    }
}