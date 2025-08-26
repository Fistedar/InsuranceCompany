package org.javaguru.travel.insurance.core.repositories.export;

import org.javaguru.travel.insurance.core.domain.export.AgreementUuidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public interface AgreementUuidRepository extends JpaRepository<AgreementUuidEntity, String> {

    @Query("""
            select au from AgreementUuidEntity au
            where au.timeDate >= :dateTimeFrom and au.timeDate <= :dateTimeTo
            """)
    List<AgreementUuidEntity> findByTimeDate(@Param("dateTimeFrom") LocalDateTime dateTimeFrom,
                                             @Param("dateTimeTo") LocalDateTime dateTimeTo);

    void deleteByUuid(String uuid);
}
