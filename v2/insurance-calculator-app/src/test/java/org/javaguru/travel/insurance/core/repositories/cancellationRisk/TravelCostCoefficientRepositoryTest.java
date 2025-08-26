package org.javaguru.travel.insurance.core.repositories.cancellationRisk;

import org.javaguru.travel.insurance.core.domain.cancellationRisk.TravelCostCoefficientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TravelCostCoefficientRepositoryTest {

    @Autowired
    private TravelCostCoefficientRepository repository;

    @Test
    @DisplayName("Test: Table TravelCostCoefficient is present")
    void testTableTravelCostCoefficientIsPresent() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Test: Can find by travelCost")
    void testCanFindByTravelCost() {
        Optional<TravelCostCoefficientEntity> entity = repository.findTravelCostCoefficientByCost(new BigDecimal("1000"));
        assertTrue(entity.isPresent());
        assertEquals(entity.get().getCoefficient(), new BigDecimal("10.00"));
    }
}