package org.javaguru.travel.insurance.core.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    private String personFirstName;

    private String medicalRiskLimitLevel;
    private String personCode;
    private String personLastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate personBirthDate;
    private BigDecimal travelCost;

    private List<RiskDTO> risks;

}
