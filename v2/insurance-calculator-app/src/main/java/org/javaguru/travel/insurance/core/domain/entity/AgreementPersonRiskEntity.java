package org.javaguru.travel.insurance.core.domain.entity;

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

@Entity
@Table(name = "agreement_person_risks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgreementPersonRiskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "agreement_person_id",nullable = false)
    @ManyToOne
    private AgreementPersonEntity agreementPerson;

    @Column(name = "risk_ic",nullable = false)
    private String riskIc;

    @Column(name = "premium",nullable = false)
    private BigDecimal premium;

}
