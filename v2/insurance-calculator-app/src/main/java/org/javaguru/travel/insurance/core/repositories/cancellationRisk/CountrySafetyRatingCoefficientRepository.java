package org.javaguru.travel.insurance.core.repositories.cancellationRisk;

import java.util.Optional;
import org.javaguru.travel.insurance.core.domain.cancellationRisk.CountrySafetyRatingCoefficientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountrySafetyRatingCoefficientRepository
        extends JpaRepository<CountrySafetyRatingCoefficientEntity, Long> {

    Optional<CountrySafetyRatingCoefficientEntity> findByCountry(String country);
}
