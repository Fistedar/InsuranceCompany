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
public class PersonCodeFormatValidation extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (personCodeIsNotEmpty(person)
                && invalidFormatPersonCode(person))
                ? Optional.of(buildError(person))
                : Optional.empty();
    }


    private boolean personCodeIsNotEmpty(PersonDTO person) {
        return person.getPersonCode() != null && !person.getPersonCode().isEmpty();
    }

    private boolean invalidFormatPersonCode(PersonDTO person) {
        return !person.getPersonCode().matches("^\\d{6}-\\d{5}$");
    }

    private ValidationErrorDTO buildError(PersonDTO person) {
        Placeholder placeholder = new Placeholder("PersonCode", person.getPersonCode());
        return errorFactory.buildError("ERROR_CODE_21", placeholder);
    }
}
