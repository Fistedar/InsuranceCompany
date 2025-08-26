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

@Table(name = "agreement_person")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgreementPersonEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "person_id", nullable = false)
    @ManyToOne
    private PersonEntity person;

    @JoinColumn(name = "agreement_id", nullable = false)
    @ManyToOne
    private AgreementEntity agreement;

    @Column(name = "medical_risk",nullable = false)
    private String medicalRisk;
}
