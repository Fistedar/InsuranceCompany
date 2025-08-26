package org.javaguru.travel.insurance.core.repositories.cancellationRisk;

import org.javaguru.travel.insurance.core.domain.cancellationRisk.TravelCostCoefficientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.Optional;

public interface TravelCostCoefficientRepository extends JpaRepository<TravelCostCoefficientEntity, Long> {

    @Query("""
            select tc from TravelCostCoefficientEntity tc
            where tc.travelCostFrom < :cost
            and  tc.travelCostTo >= :cost
            """)
    Optional<TravelCostCoefficientEntity> findTravelCostCoefficientByCost(@Param("cost") BigDecimal cost);
}
