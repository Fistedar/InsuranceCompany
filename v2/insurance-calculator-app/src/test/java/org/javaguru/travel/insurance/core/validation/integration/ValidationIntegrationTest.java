package org.javaguru.travel.insurance.core.validation.integration;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.TravelAgreementValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidation validator;

    @Test
    public void shouldReturnErrorWhenDateFromIsNull() {
        AgreementDTO agreement = createAgreement();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertThat(errors)
                .extracting(ValidationErrorDTO::getErrorCode)
                .containsExactlyInAnyOrder(
                        "ERROR_CODE_1",
                        "ERROR_CODE_2",
                        "ERROR_CODE_7",
                        "ERROR_CODE_9",
                        "ERROR_CODE_9",
                        "ERROR_CODE_11",
                        "ERROR_CODE_12",
                        "ERROR_CODE_15",
                        "ERROR_CODE_17"
                );
    }

    private PersonDTO createPerson() {
        return PersonDTO.builder()
                .personFirstName("")
                .personLastName(null)
                .personCode("")
                .personBirthDate(LocalDate.of(2100, 1, 1))
                .medicalRiskLimitLevel("LEVEL_100")
                .build();
    }

    private AgreementDTO createAgreement() {
        return AgreementDTO.builder()
                .country("Russyia")
                .agreementDateFrom(LocalDate.of(2039, 1, 1))
                .agreementDateTo(LocalDate.of(2030, 1, 1))
                .persons(List.of(createPerson()))
                .agreementPremium(BigDecimal.ONE)
                .selectedRisks(List.of("I forgot my egg", "Shit, It's MissClick"))
                .build();
    }
}
