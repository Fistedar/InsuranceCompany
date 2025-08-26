package org.javaguru.travel.insurance.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javaguru.travel.insurance.dto.util.BigDecimalSerializer;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelRisk {

    private String riskIc;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal premium;
}
