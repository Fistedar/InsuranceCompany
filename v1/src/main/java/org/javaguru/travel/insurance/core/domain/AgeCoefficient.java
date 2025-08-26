package org.javaguru.travel.insurance.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "age_coefficient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgeCoefficient {

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
