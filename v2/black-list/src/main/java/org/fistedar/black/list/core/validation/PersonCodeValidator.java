package org.fistedar.black.list.core.validation;

import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class PersonCodeValidator implements PersonValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(PersonDTO person) {
        return (person.getPersonCode()!= null
        && !person.getPersonCode().isBlank())
                ? Optional.empty()
                : Optional.of(errorFactory.buildError("ERROR_CODE_3"));
    }
}
