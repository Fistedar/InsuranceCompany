package org.fistedar.doc.generator.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


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
