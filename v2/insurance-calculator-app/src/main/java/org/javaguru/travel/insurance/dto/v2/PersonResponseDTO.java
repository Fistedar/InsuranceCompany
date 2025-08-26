package org.javaguru.travel.insurance.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.dto.TravelRisk;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonResponseDTO {

    private String personFirstName;
    private String personLastName;
    private BigDecimal personPremium;
    private List<TravelRisk> risks;
    private String medicalRiskLimitLevel;
    private String personCode;
    private BigDecimal travelCost;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate personBirthDate;

}
