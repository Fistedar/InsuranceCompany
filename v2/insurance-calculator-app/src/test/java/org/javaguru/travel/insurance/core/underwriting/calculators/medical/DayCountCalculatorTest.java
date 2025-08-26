package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
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
    private AgreementDTO agreementDTO;
    @Mock
    private DateTimeUtil dateTimeUtil;
    @InjectMocks
    private DayCountCalculator dayCountCalculator;

    @Test
    void calculateDaysCount() {
        when(agreementDTO.getAgreementDateFrom()).thenReturn(LocalDate.of(2020, 4, 4));
        when(agreementDTO.getAgreementDateTo()).thenReturn(LocalDate.of(2020, 4, 7));
        when(dateTimeUtil.getDaysBetween(agreementDTO.getAgreementDateFrom(), agreementDTO.getAgreementDateTo()))
                .thenReturn(3L);

        assertEquals(new BigDecimal("3"), dayCountCalculator.calculateDaysCount(agreementDTO));
    }
}