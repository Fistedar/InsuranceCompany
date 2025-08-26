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
@Table(name = "country_default_day_rate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDefaultDayRate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classifier_value_id", nullable = false)
    private ClassifierValue classifierValue;

    @Column(name = "default_day_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal defaultDayRate;
}
