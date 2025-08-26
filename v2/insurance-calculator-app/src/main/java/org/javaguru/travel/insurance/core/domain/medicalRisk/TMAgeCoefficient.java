package org.javaguru.travel.insurance.core.domain.medicalRisk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tm_age_coefficient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TMAgeCoefficient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "age_from")
    private int ageFrom;

    @Column(name = "age_to")
    private int ageTo;

    @Column(name = "coefficient")
    private BigDecimal coefficient;
}
