package org.javaguru.travel.insurance.core.service;

import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.service.database.loaders.AgreementDTOLoader;
import org.javaguru.travel.insurance.core.validation.internal.TravelAgreementUuidValidation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelGetAgreementInternalImplTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private TravelGetAgreementCoreCommand coreCommand;
    @Mock
    private TravelAgreementUuidValidation validator;
    @Mock
    private AgreementDTOLoader agreementDTOLoader;
    @InjectMocks
    private TravelGetAgreementInternalImpl travelGetAgreementInternal;

    @Test
    @DisplayName("Test: Build response with errors")
    void shouldBuildResponseWithErrors_whenValidationFails() {
        when(coreCommand.getUuid()).thenReturn(" ");
        when(validator.validate(coreCommand.getUuid())).thenReturn(List.of(new ValidationErrorDTO()));

        assertTrue(travelGetAgreementInternal.getAgreementInternal(coreCommand).hasErrors());
    }

    @Test
    @DisplayName("Test: Build succeed response")
    void shouldBuildResponse_whenValidationSucceeds() {
        when(coreCommand.getUuid()).thenReturn("valid");
        when(validator.validate(coreCommand.getUuid())).thenReturn(List.of());
        when(agreementDTOLoader.loadAgreementDTOByUuid(coreCommand.getUuid())).thenReturn(agreementDTO);

        assertFalse(travelGetAgreementInternal.getAgreementInternal(coreCommand).hasErrors());
        assertEquals(travelGetAgreementInternal.getAgreementInternal(coreCommand).getAgreement(), agreementDTO);

    }
}