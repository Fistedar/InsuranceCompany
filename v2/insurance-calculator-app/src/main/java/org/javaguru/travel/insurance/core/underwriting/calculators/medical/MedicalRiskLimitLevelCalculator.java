package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.medicalRisk.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.MedicalRiskLimitLevelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class MedicalRiskLimitLevelCalculator {

    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    BigDecimal calculateMedicalRisk(PersonDTO personDTO) {
        return medicalRiskLimitLevelEnabled
                ? getMedicalRisk(personDTO)
                : getDefaultMedicalRisk();
    }

    private BigDecimal getMedicalRisk(PersonDTO personDTO) {
        return medicalRiskLimitLevelRepository.findByMedicalRiskLimit(personDTO.getMedicalRiskLimitLevel())
                .map(MedicalRiskLimitLevel::getCoefficient)
                .orElseThrow(() -> new RuntimeException("This " + personDTO.getMedicalRiskLimitLevel() + " medicalRiskLimit unsupported"));
    }

    private BigDecimal getDefaultMedicalRisk() {
        return BigDecimal.ONE;
    }

}
