package org.javaguru.travel.insurance.core.repositories.entity;

import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entity.AgreementPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgreementPersonRepository extends JpaRepository<AgreementPersonEntity, Long> {

    List<AgreementPersonEntity> findByAgreement(AgreementEntity entity);
}
