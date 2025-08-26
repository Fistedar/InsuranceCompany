package org.javaguru.travel.insurance.core.domain.cancellationRisk;

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
@Table(name = "tc_age_coefficient")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TCAgeCoefficientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "age_from")
    private int age_from;

    @Column(name = "age_to")
    private int age_to;

    @Column(name = "coefficient")
    private BigDecimal coefficient;
}
