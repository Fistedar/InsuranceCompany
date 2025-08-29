package org.fistedar.black.list.core.validation;

import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class BlackListValidationImpl implements BlackListValidation {

    private final List<PersonValidation> personValidations;

    @Override
    public List<ValidationErrorDTO> validate(PersonDTO person) {
        return personValidations.stream()
                .map(personValidation -> personValidation.validate(person))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
