package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.medicalRisk.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.CountryDefaultDayRateRepository;
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
class CountryDefaultDayRateRepositoryTest {

    @Autowired
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Test
    @DisplayName("Test: countryDefaultDayRate table is present")
    void injectRepositoryIsNotNull() {
        assertNotNull(countryDefaultDayRateRepository);
    }

    @Test
    @DisplayName("Test: Can find entity by Country ")
    void findByCountryValid() {
        Optional<CountryDefaultDayRate> entity = countryDefaultDayRateRepository.findByCountryIc("LATVIA");

        assertTrue(entity.isPresent());
        assertEquals(new BigDecimal("1.00"), entity.orElseThrow().getDefaultDayRate());
    }

    @Test
    @DisplayName("Test: Can't find entity by FakeCountry ")
    void findByCountryInValid() {
        Optional<CountryDefaultDayRate> entity = countryDefaultDayRateRepository.findByCountryIc("LATVA");

        assertTrue(entity.isEmpty());
    }
}