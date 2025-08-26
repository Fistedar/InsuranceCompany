package org.javaguru.travel.insurance.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "selected_risks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedRiskEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "selected_risk", nullable = false)
    private String selectedRisk;

    @JoinColumn(name = "agreement_id", nullable = false)
    @ManyToOne
    private AgreementEntity agreement;
}
