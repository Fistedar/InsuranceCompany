package org.javaguru.travel.insurance.core.repositories.entity;

import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonRiskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AgreementPersonRiskRepository extends JpaRepository<AgreementPersonRiskEntity, Long> {

    List<AgreementPersonRiskEntity> findByAgreementPerson(AgreementPersonEntity agreementPerson);
}
