package org.javaguru.travel.insurance.core.validation.person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.blacklist.BlackListChecker;
import org.javaguru.travel.insurance.core.exceptions.blacklist.BlackListClientException;
import org.javaguru.travel.insurance.core.exceptions.blacklist.BlackListUnavailableException;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
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
        try {
            if (blackListChecker.checkBlackList(person)) {
                Placeholder placeholder = new Placeholder("PersonCode", person.getPersonCode());
                return Optional.of(errorFactory.buildError("ERROR_CODE_24", placeholder));
            }
        } catch (BlackListUnavailableException e) {
            // Сервис недоступен
            log.warn("BlackList service unavailable: {}", e.getMessage());
            return Optional.of(errorFactory.buildError("ERROR_CODE_25"));
        } catch (BlackListClientException e) {
            // Ошибка клиента (4xx)
            log.error("BlackList client error: {}", e.getMessage());
            return Optional.of(errorFactory.buildError("ERROR_CODE_26"));
        } catch (Exception e) {
            // Неожиданные ошибки
            log.error("Unexpected error during blacklist check: {}", e.getMessage());
            return Optional.empty(); // или другая обработка
        }
        return Optional.empty();
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
