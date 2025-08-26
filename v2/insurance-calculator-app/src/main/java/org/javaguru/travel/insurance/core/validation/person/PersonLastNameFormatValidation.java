package org.javaguru.travel.insurance.core.validation.person;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonLastNameFormatValidation extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (nameIsNotEmpty(person)
                && invalidFormat(person))
                ? Optional.of(buildError(person))
                : Optional.empty();
    }

    private ValidationErrorDTO buildError(PersonDTO person) {
        Placeholder placeholder = new Placeholder("LastName", person.getPersonLastName());
        return errorFactory.buildError("ERROR_CODE_23", placeholder);
    }

    private boolean nameIsNotEmpty(PersonDTO person) {
        return person.getPersonLastName() != null && !person.getPersonLastName().isBlank();
    }

    private boolean invalidFormat(PersonDTO person) {
        return !person.getPersonLastName().matches("^[a-zA-Z\\s-]+$");
    }
}
