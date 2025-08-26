package org.javaguru.travel.insurance.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.dto.TravelRisk;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDTO {

    private String personFirstName;
    private String personLastName;
    private BigDecimal personPremium;
    private List<TravelRisk> risks;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate personBirthDate;

}
