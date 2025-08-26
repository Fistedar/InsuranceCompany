package org.javaguru.travel.insurance.core.repositories.cancellationRisk;

import org.javaguru.travel.insurance.core.domain.cancellationRisk.CountrySafetyRatingCoefficientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CountrySafetyRatingCoefficientRepositoryTest {

    @Autowired
    private CountrySafetyRatingCoefficientRepository repository;

    @Test
    @DisplayName("Test: Table CountrySafetyRatingCoefficient is present")
    void shouldBePresent() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Test: Can find by country")
    void findByCountry() {
        Optional<CountrySafetyRatingCoefficientEntity> coefficient = repository.findByCountry("LATVIA");
        assertTrue(coefficient.isPresent());
        assertEquals(coefficient.get().getCountry(), "LATVIA");
    }

    @Test
    @DisplayName("Test: Can't find by country")
    void findByCountryNotFound() {
        assertTrue(repository.findByCountry("KZ").isEmpty());
    }
}