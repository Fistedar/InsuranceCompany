package org.javaguru.travel.insurance.core.repositories.entity;

import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgreementRepository extends JpaRepository<AgreementEntity, Long> {

    Optional<AgreementEntity> findByUuid(String uuid);
}
