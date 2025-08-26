package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class MedicalRiskLimitLevelCalculator {

    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    BigDecimal calculateMedicalRisk(TravelCalculatePremiumRequestV1 request){
        return medicalRiskLimitLevelEnabled
                ? getMedicalRisk(request)
                : getDefaultMedicalRisk();
    }

   private BigDecimal getMedicalRisk(TravelCalculatePremiumRequestV1 request){
       return medicalRiskLimitLevelRepository.findByMedicalRiskLimit(request.getMedicalRiskLimitLevel())
                .map(MedicalRiskLimitLevel::getCoefficient)
                .orElseThrow(()-> new RuntimeException("This " + request.getMedicalRiskLimitLevel() + " medicalRiskLimit unsupported"));
    }

    private BigDecimal getDefaultMedicalRisk(){
        return BigDecimal.ONE;
    }

}
