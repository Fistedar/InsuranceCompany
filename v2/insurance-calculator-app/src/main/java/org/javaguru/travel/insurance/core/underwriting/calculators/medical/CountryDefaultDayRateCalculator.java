package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.medicalRisk.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.CountryDefaultDayRateRepository;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CountryDefaultDayRateCalculator {

    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    BigDecimal calculateDailyRate(AgreementDTO agreementDTO) {
        return countryDefaultDayRateRepository.findByCountryIc(agreementDTO.getCountry())
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(() -> new RuntimeException("Country day rate not found by country id = " + agreementDTO.getCountry()));
    }
}
