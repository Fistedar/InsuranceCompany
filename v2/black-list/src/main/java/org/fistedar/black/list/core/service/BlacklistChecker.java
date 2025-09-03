package org.fistedar.black.list.core.service;

import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.repository.PersonsRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BlacklistChecker {

    private final PersonsRepository personsRepository;

    public PersonDTO isBlacklisted(BlacklistCoreCommand command) {
        PersonDTO personDTO = command.getPerson();
        String personCode = personDTO.getPersonCode();
        String personFirstName = personDTO.getPersonFirstName();
        String personLastName = personDTO.getPersonLastName();
        Boolean inBlackList = personsRepository.findByPersonCodeAndPersonFirstNameAndPersonLastName(
                personCode,personFirstName,personLastName).isPresent();
        personDTO.setBlackListed(inBlackList);
        return personDTO;
    }


}
