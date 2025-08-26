package org.javaguru.travel.insurance.core.repositories.cancellationRisk;

import org.javaguru.travel.insurance.core.domain.cancellationRisk.TCAgeCoefficientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface TCAgeCoefficientRepository extends JpaRepository<TCAgeCoefficientEntity, Long> {

    @Query("""
            SELECT ac FROM TCAgeCoefficientEntity ac
            where ac.age_from <= :age
            and ac.age_to >= :age
            """)
    Optional<TCAgeCoefficientEntity> findByAge(@Param("age") int age);
}
