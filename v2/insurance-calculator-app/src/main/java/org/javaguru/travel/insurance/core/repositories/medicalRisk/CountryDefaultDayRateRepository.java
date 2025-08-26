package org.javaguru.travel.insurance.core.repositories.medicalRisk;

import org.javaguru.travel.insurance.core.domain.medicalRisk.CountryDefaultDayRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CountryDefaultDayRateRepository extends JpaRepository<CountryDefaultDayRate, Long> {
    @Query("""
            SELECT cd from CountryDefaultDayRate cd
            left join cd.classifierValue сv
            where сv.ic = :countryIc
            """)
    Optional<CountryDefaultDayRate> findByCountryIc(
            @Param("countryIc") String countryIc
    );
}
