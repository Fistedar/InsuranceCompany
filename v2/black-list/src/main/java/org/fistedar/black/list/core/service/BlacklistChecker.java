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
        personDTO.setBlackListed(personsRepository.findByPersonCode(personDTO.getPersonCode()).isPresent());
        return personDTO;
    }

}
