package org.javaguru.travel.insurance.core.underwriting.integration.cancellation;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class TravelCancellationUnderwritingIntegrationTest {

    @Qualifier("travelCancellationRiskCalculator")
    @Autowired
    private TravelRiskPremiumCalculator travelRiskPremiumCalculator;

    private AgreementDTO agreementDTO;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        personDTO = createPerson();
        agreementDTO = createAgreement(personDTO);
    }

    @Test
    @DisplayName("Test: Cancellation risk disabled")
    void shouldReturnZero_whenCancellationDisabled() {
        ReflectionTestUtils.setField(travelRiskPremiumCalculator, "cancellationRiskEnabled", Boolean.FALSE);

        BigDecimal premium = travelRiskPremiumCalculator.calculateSingleRiskPremium(agreementDTO, personDTO);
        assertThat(premium).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Test: Cancellation risk enabled")
    void shouldReturnPremium_whenCancellationEnabled() {
        ReflectionTestUtils.setField(travelRiskPremiumCalculator, "cancellationRiskEnabled", Boolean.TRUE);

        BigDecimal premium = travelRiskPremiumCalculator.calculateSingleRiskPremium(agreementDTO, personDTO);
        assertThat(premium).isEqualByComparingTo(new BigDecimal("7530.00"));
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
