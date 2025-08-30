package org.fistedar.black.list.core.service;

import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;
import org.fistedar.black.list.core.api.command.BlacklistCoreResult;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.fistedar.black.list.core.validation.BlackListValidation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class BlacklistServiceImpl implements BlacklistService {

    private final BlackListValidation blackListValidation;
    private final BlacklistChecker blacklistChecker;

    @Override
    public BlacklistCoreResult checkPersonFromBlacklist(BlacklistCoreCommand command) {
        List<ValidationErrorDTO> errors = blackListValidation.validate(command.getPerson());
        if (errors.isEmpty()) {
            PersonDTO person = blacklistChecker.isBlacklisted(command);
            return buildSuccessResponse(person);
        } else {
            return buildErrorResponse(errors);
        }
    }

    private BlacklistCoreResult buildErrorResponse(List<ValidationErrorDTO> errors) {
        return new BlacklistCoreResult(errors);
    }

    private BlacklistCoreResult buildSuccessResponse(PersonDTO person) {
        return new BlacklistCoreResult(null, person);
    }
}
