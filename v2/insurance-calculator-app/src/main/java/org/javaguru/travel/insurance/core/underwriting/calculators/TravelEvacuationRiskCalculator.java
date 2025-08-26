package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
class TravelEvacuationRiskCalculator implements TravelRiskPremiumCalculator {


    @Override
    public BigDecimal calculateSingleRiskPremium(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return BigDecimal.ZERO;
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_EVACUATION";
    }
}
