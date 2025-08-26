package org.javaguru.travel.insurance.core.validation.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.domain.medicalRisk.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelExistsInDatabaseAgreementValidationTest {

    @Mock
    private PersonDTO person;
    @Mock
    private AgreementDTO agreement;
    @Mock
    private ValidationErrorFactory errorFactory;
    @Mock
    private MedicalRiskLimitLevelRepository repository;
    @InjectMocks
    private MedicalRiskLimitLevelExistsInDatabaseAgreementValidation validator;

    @Test
    void shouldEmptyOptional_whenMedicalRiskLimitLevelExistsInDatabase() {
        when(person.getMedicalRiskLimitLevel()).thenReturn("LEVEL_1");
        when(repository.findByMedicalRiskLimit("LEVEL_1"))
                .thenReturn(Optional.of(mock(MedicalRiskLimitLevel.class)));

        assertTrue(validator.validate(agreement, person).isEmpty());
    }

    @Test
    void shouldError_whenMedicalRiskLimitLevelNotExistsInDatabase() {
        when(person.getMedicalRiskLimitLevel()).thenReturn("LEVEL_2");
        when(repository.findByMedicalRiskLimit("LEVEL_2"))
                .thenReturn(Optional.empty());
        when(errorFactory.buildError(eq("ERROR_CODE_15"), any(Placeholder.class)))
                .thenReturn(mock(ValidationErrorDTO.class));

        assertTrue(validator.validate(agreement, person).isPresent());
    }
}