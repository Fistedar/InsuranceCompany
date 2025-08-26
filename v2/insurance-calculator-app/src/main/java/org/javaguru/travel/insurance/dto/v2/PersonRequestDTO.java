package org.javaguru.travel.insurance.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequestDTO {

    @Size(max = 50, message = "First name must be less than 50 characters")
    private String personFirstName;
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String personLastName;
    private String medicalRiskLimitLevel;
    private String personCode;
    private BigDecimal travelCost;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate personBirthDate;
}
