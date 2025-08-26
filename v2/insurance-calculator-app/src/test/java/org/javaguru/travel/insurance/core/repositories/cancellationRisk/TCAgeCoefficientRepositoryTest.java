package org.javaguru.travel.insurance.core.repositories.cancellationRisk;

import org.javaguru.travel.insurance.core.domain.cancellationRisk.TCAgeCoefficientEntity;
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
class TCAgeCoefficientRepositoryTest {

    @Autowired
    private TCAgeCoefficientRepository repository;

    @Test
    @DisplayName("Test: Table TCAgeCoefficient is present")
    void testTableTCAgeCoefficientIsPresent() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Test: Can find coefficient by age")
    void testCanFindCoefficientByAge() {
        Optional<TCAgeCoefficientEntity> entity = repository.findByAge(20);
        assertTrue(entity.isPresent());
        assertEquals(entity.get().getCoefficient(), new BigDecimal("20.00"));
    }

    @Test
    @DisplayName("Test: Can't find coefficient by age")
    void testCanNotFindCoefficientByAge() {
        assertTrue(repository.findByAge(150).isEmpty());
    }
}