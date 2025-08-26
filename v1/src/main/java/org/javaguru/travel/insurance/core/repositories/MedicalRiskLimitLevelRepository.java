package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MedicalRiskLimitLevelRepository extends JpaRepository<MedicalRiskLimitLevel, Long> {

    @Query("select mr from MedicalRiskLimitLevel mr" +
            " left join mr.classifierValue cv " +
            " where cv.ic =:medicalRiskLimitLevelIc ")
    Optional<MedicalRiskLimitLevel> findByMedicalRiskLimit(@Param(value = "medicalRiskLimitLevelIc") String medicalRiskLimitLevelIc);

}
