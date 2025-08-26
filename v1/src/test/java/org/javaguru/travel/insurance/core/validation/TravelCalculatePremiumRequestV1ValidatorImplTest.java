package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumRequestV1ValidatorImplTest {

    @Mock
    private RequestValidator requestValidator;
    @Mock
    private RequestValidationList requestValidationList;
    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @InjectMocks
    private TravelCalculatePremiumRequestValidatorImpl validator;

    @BeforeEach
    void setUp() {
        List<RequestValidator> travelSingleValidations = List.of(requestValidator);
        List<RequestValidationList> travelListValidations = List.of(requestValidationList);

        validator = new TravelCalculatePremiumRequestValidatorImpl(travelSingleValidations, travelListValidations);
    }

    @Test
    void shouldNotReturnErrors() {
        when(requestValidator.validate(request)).thenReturn(Optional.empty());
        when(requestValidationList.validate(request)).thenReturn(List.of());

        List<ValidationError> errors = validator.validate(request);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnsSingleErrors() {
        when(requestValidator.validate(request)).thenReturn(Optional.empty());
        when(requestValidationList.validate(request))
                .thenReturn(List.of(new ValidationError("Test", "Test"),
                        new ValidationError("Test2", "Test2")));

        List<ValidationError> errors = validator.validate(request);
        assertEquals(2, errors.size());
    }

    @Test
    void shouldReturnsListErrors() {
        when(requestValidator.validate(request)).thenReturn(Optional.of(new ValidationError("Test", "Test")));
        when(requestValidationList.validate(request)).thenReturn(List.of());

        List<ValidationError> errors = validator.validate(request);
        assertEquals(1, errors.size());
    }

}