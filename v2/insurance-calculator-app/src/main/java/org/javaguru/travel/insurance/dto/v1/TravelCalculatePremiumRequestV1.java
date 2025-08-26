package org.javaguru.travel.insurance.dto.v1;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TravelCalculatePremiumRequestV1 {

    @Size(max = 50, message = "First name must be less than 50 characters")
    private String personFirstName;
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String personLastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate agreementDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate personBirthDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate agreementDateTo;

    private BigDecimal travelCost;
    private String personCode;
    private String medicalRiskLimitLevel;

    @JsonAlias("selected_risks")
    private List<String> selectedRisks;
    private String country;
}
