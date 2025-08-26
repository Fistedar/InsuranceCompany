package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DayCountCalculatorTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private DayCountCalculator dayCountCalculator;

    @Test
    void calculateDaysCount() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2020, 4, 4));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2020, 4, 7));
        when(dateTimeUtil.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(3L);

        assertEquals(new BigDecimal("3"), dayCountCalculator.calculateDaysCount(request));
    }
}