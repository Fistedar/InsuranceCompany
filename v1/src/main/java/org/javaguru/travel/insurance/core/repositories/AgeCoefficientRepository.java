package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AgeCoefficientRepository extends JpaRepository<AgeCoefficient, Long> {
    @Query("select ac from AgeCoefficient ac" +
            " where :age >= ac.ageFrom and" +
            " :age <= ac.ageTo")
    Optional<AgeCoefficient> findByAgeFrom(@Param("age") Integer age);
}
