package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.cancellationRisk.CountrySafetyRatingCoefficientEntity;
import org.javaguru.travel.insurance.core.repositories.cancellationRisk.CountrySafetyRatingCoefficientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountrySafetyRatingCoefficientCalculatorTest {

    private final static String COUNTRY = "RUSSIA";

    @Mock
    private PersonDTO person;
    @Mock
    private CountrySafetyRatingCoefficientEntity ratingEntity;
    @Mock
    private CountrySafetyRatingCoefficientRepository repository;
    @InjectMocks
    private CountrySafetyRatingCoefficientCalculator calculator;

    @Test
    void shouldReturnException_whenCountryUnsupported() {
        when(repository.findByCountry(COUNTRY)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> calculator.getCountrySafetyRatingCoefficient("RUSSIA", person));
    }

    @Test
    void shouldReturnException_whenCoefficientIsZero() {
        when(ratingEntity.getCoefficient()).thenReturn(BigDecimal.ZERO);
        when(repository.findByCountry(COUNTRY)).thenReturn(Optional.of(ratingEntity));

        assertThrows(ArithmeticException.class, () -> calculator.getCountrySafetyRatingCoefficient("RUSSIA", person));
    }

    @Test
    void shouldReturnCoefficient_whenGetValidData() {
        when(repository.findByCountry(COUNTRY)).thenReturn(Optional.of(ratingEntity));
        when(ratingEntity.getCoefficient()).thenReturn(BigDecimal.ONE);
        when(person.getTravelCost()).thenReturn(BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(100), calculator.getCountrySafetyRatingCoefficient(COUNTRY, person));
    }
}