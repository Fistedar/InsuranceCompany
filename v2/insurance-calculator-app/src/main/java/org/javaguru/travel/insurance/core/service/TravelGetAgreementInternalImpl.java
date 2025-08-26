package org.javaguru.travel.insurance.core.service;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.service.database.loaders.AgreementDTOLoader;
import org.javaguru.travel.insurance.core.validation.internal.TravelAgreementUuidValidation;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
class TravelGetAgreementInternalImpl implements TravelGetAgreementInternal {

    private final TravelAgreementUuidValidation travelAgreementUuidValidation;
    private final AgreementDTOLoader agreementDTOLoader;

    @Override
    public TravelGetAgreementCoreResult getAgreementInternal(TravelGetAgreementCoreCommand command) {
        List<ValidationErrorDTO> errors = travelAgreementUuidValidation.validate(command.getUuid());
        if (errors.isEmpty()) {
            AgreementDTO agreement = agreementDTOLoader.loadAgreementDTOByUuid(command.getUuid());
            return buildResponse(agreement);
        } else {
            return buildResponse(errors);
        }
    }

    private TravelGetAgreementCoreResult buildResponse(List<ValidationErrorDTO> errors) {
        return new TravelGetAgreementCoreResult(errors);
    }

    private TravelGetAgreementCoreResult buildResponse(AgreementDTO agreementDTO) {
        return new TravelGetAgreementCoreResult(agreementDTO, null);
    }
}
