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

@Table(name = "travel_cost_coefficient")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelCostCoefficientEntity {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "travel_cost_from",nullable = false)
    private BigDecimal travelCostFrom;

    @Column(name = "travel_cost_to",nullable = false)
    private BigDecimal travelCostTo;

    @Column(name = "coefficient", nullable = false)
    private BigDecimal coefficient;
}
