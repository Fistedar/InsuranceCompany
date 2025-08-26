package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DayCountCalculator {

    private final DateTimeUtil dateTimeUtil;

    BigDecimal calculateDaysCount(AgreementDTO agreementDTO) {
        long days = dateTimeUtil.getDaysBetween(
                agreementDTO.getAgreementDateFrom(),
                agreementDTO.getAgreementDateTo());
        return BigDecimal.valueOf(days);
    }

}
