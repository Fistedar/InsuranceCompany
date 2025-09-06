package org.javaguru.travel.insurance.core.validation.person;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.blacklist.BlackListChecker;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BlackListPersonValidation extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;
    private final BlackListChecker blackListChecker;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (!personFirstNameIsEmpty(person)
                && !personLastNameIsEmpty(person)
                && !personCodeIsEmpty(person))
                ? checkBlackList(person)
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> checkBlackList(PersonDTO person) {
        if (blackListChecker.checkBlackList(person)) {
            Placeholder placeholder = new Placeholder("PersonCode", person.getPersonCode());
            return Optional.of(errorFactory.buildError("ERROR_CODE_24",placeholder));
        } else {
            return Optional.empty();
        }
    }

    private boolean personFirstNameIsEmpty(PersonDTO person) {
        return person.getPersonFirstName() == null || person.getPersonFirstName().isBlank();
    }

    private boolean personLastNameIsEmpty(PersonDTO person) {
        return person.getPersonLastName() == null || person.getPersonLastName().isBlank();
    }

    private boolean personCodeIsEmpty(PersonDTO person) {
        return person.getPersonCode() == null || person.getPersonCode().isBlank();
    }
}
