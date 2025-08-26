package org.javaguru.travel.insurance.core.repositories.entity;

import org.javaguru.travel.insurance.core.domain.entity.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entity.SelectedRiskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SelectedRisksRepository extends JpaRepository<SelectedRiskEntity, Long> {

    List<SelectedRiskEntity> findByAgreement(AgreementEntity agreement);
}
