package org.javaguru.travel.insurance.core.service.database.savers;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entity.PersonEntity;
import org.javaguru.travel.insurance.core.repositories.entity.PersonsRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class PersonSaver {

    private final PersonsRepository personsRepository;

    PersonEntity createAndSaveEntity(PersonDTO personDTO) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(personDTO.getPersonFirstName());
        personEntity.setLastName(personDTO.getPersonLastName());
        personEntity.setPersonCode(personDTO.getPersonCode());
        personEntity.setBirthDate(personDTO.getPersonBirthDate());
        return (findInDatabase(personDTO).isEmpty())
                ? personsRepository.save(personEntity)
                : findInDatabase(personDTO).orElseThrow();
    }

    private Optional<PersonEntity> findInDatabase(PersonDTO person) {
        return personsRepository.findByFirstNameAndLastNameAndPersonCode(
                person.getPersonFirstName(),
                person.getPersonLastName(),
                person.getPersonCode());
    }
}
