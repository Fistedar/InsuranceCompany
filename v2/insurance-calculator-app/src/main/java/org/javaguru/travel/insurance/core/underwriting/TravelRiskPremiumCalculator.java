package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import java.math.BigDecimal;

public interface TravelRiskPremiumCalculator {
    BigDecimal calculateSingleRiskPremium(AgreementDTO agreementDTO, PersonDTO personDTO);

    String getRiskIc();
}
