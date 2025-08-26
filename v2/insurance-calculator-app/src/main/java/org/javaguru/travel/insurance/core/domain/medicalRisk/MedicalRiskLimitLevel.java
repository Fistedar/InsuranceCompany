package org.javaguru.travel.insurance.core.domain.medicalRisk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.core.domain.ClassifierValue;

@Entity
@Table(name = "medical_risk_limit_level")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRiskLimitLevel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "classifier_value_id", nullable = false)
    @ManyToOne
    private ClassifierValue classifierValue;

    @Column(name = "coefficient", scale = 2, precision = 10, nullable = false)
    private BigDecimal coefficient;
}
