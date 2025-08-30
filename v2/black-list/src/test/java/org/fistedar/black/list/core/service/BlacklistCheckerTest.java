package org.fistedar.black.list.core.service;

import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.domain.PersonEntity;
import org.fistedar.black.list.core.repository.PersonsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlacklistCheckerTest {

    @Mock
    private BlacklistCoreCommand command;
    @Mock
    private PersonsRepository personsRepository;
    @InjectMocks
    private BlacklistChecker blacklistChecker;

    @Test
    void shouldReturnTrue_whenPersonIsBlacklisted() {
        PersonDTO person = new PersonDTO();
        when(command.getPerson()).thenReturn(person);
        when(personsRepository.findByPersonCode(person.getPersonCode()))
                .thenReturn(Optional.of(new PersonEntity()));

        PersonDTO expectedPerson = blacklistChecker.isBlacklisted(command);

        assertTrue(expectedPerson.getBlackListed());
        verify(personsRepository).findByPersonCode(person.getPersonCode());
    }

    @Test
    void shouldReturnFalse_whenPersonIsNotBlacklisted() {
        PersonDTO person = new PersonDTO();
        when(command.getPerson()).thenReturn(person);
        when(personsRepository.findByPersonCode(person.getPersonCode()))
                .thenReturn(Optional.empty());

        PersonDTO expectedPerson = blacklistChecker.isBlacklisted(command);

        assertFalse(expectedPerson.getBlackListed());
        verify(personsRepository).findByPersonCode(person.getPersonCode());
    }
}