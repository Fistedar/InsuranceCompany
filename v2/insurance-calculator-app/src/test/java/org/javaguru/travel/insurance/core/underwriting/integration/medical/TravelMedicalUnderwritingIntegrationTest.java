package org.javaguru.travel.insurance.core.underwriting.integration.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.core.underwriting.calculators.medical.MedicalRiskLimitLevelCalculator;
import org.javaguru.travel.insurance.core.underwriting.calculators.medical.TMAgeCoefficientCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TravelMedicalUnderwritingIntegrationTest {

    @Qualifier("travelMedicalRiskCalculator")
    @Autowired
    private TravelRiskPremiumCalculator calculator;

    @Autowired
    private TMAgeCoefficientCalculator TMAgeCoefficientCalculator;

    @Autowired
    private MedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @Test
    void shouldCalculateWithEnabledFeatures() {
        ReflectionTestUtils.setField(TMAgeCoefficientCalculator, "ageCoefficientEnabled", true);
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", true);
        PersonDTO person = createPerson();
        AgreementDTO agreement = createAgreement(person);

        BigDecimal premium = calculator.calculateSingleRiskPremium(agreement, person);
        assertThat(premium).isEqualByComparingTo("4820.64");
    }

    @Test
    void shouldCalculateWithDisabledFeatures() {
        ReflectionTestUtils.setField(TMAgeCoefficientCalculator, "ageCoefficientEnabled", true);
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", false);
        PersonDTO person = createPerson();
        AgreementDTO agreement = createAgreement(person);

        BigDecimal premium = calculator.calculateSingleRiskPremium(agreement, person);
        assertThat(premium).isEqualByComparingTo("4017.20");
    }

    @Test
    void shouldCalculateWithAgeCoefficientDisabled() {
        ReflectionTestUtils.setField(TMAgeCoefficientCalculator, "ageCoefficientEnabled", false);
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", true);
        PersonDTO person = createPerson();
        AgreementDTO agreement = createAgreement(person);

        BigDecimal premium = calculator.calculateSingleRiskPremium(agreement, person);
        assertThat(premium).isEqualByComparingTo("4382.40");
    }

    @Test
    void shouldCalculateWithMedicalRiskLimitDisabled() {
        ReflectionTestUtils.setField(TMAgeCoefficientCalculator, "ageCoefficientEnabled", false);
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", false);
        PersonDTO person = createPerson();
        AgreementDTO agreement = createAgreement(person);

        BigDecimal premium = calculator.calculateSingleRiskPremium(agreement, person);
        assertThat(premium).isEqualByComparingTo("3652.00");
    }


    private AgreementDTO createAgreement(PersonDTO person) {
        return AgreementDTO.builder()
                .country("LATVIA")
                .uuid("UUID")
                .agreementDateFrom((LocalDate.of(2030, 1, 1)))
                .agreementDateTo((LocalDate.of(2040, 1, 1)))
                .persons(List.of(person))
                .selectedRisks(List.of("TRAVEL_CANCELLATION"))
                .build();
    }

    private PersonDTO createPerson() {
        return PersonDTO.builder()
                .personFirstName("Vasya")
                .personLastName("Pupkin")
                .personBirthDate(LocalDate.of(2000, 1, 1))
                .medicalRiskLimitLevel("LEVEL_15000")
                .travelCost(new BigDecimal("1500"))
                .build();
    }
}