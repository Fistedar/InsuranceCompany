package org.javaguru.travel.insurance.core.repositories.medicalRisk;

import org.javaguru.travel.insurance.core.domain.medicalRisk.TMAgeCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface TMAgeCoefficientRepository extends JpaRepository<TMAgeCoefficient, Long> {
    @Query("""
            select ac from TMAgeCoefficient ac
            where :age >= ac.ageFrom and
            :age <= ac.ageTo
            """)
    Optional<TMAgeCoefficient> findByAgeFrom(@Param("age") Integer age);
}
