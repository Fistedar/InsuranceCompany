package org.fistedar.black.list.core.service;

import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;
import org.fistedar.black.list.core.api.command.BlacklistCoreResult;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.fistedar.black.list.core.validation.BlackListValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlacklistServiceImplTest {

    @Mock
    private BlacklistCoreCommand command;
    @Mock
    private BlackListValidation blackListValidation;
    @Mock
    private BlacklistChecker blacklistChecker;
    @InjectMocks
    private BlacklistServiceImpl blacklistService;

    @Test
    void shouldReturnSuccessResponse_whenPersonIsNotBlacklisted() {
        PersonDTO person = new PersonDTO();
        BlacklistCoreResult actualResult = new BlacklistCoreResult(null, person);
        when(command.getPerson()).thenReturn(person);
        when(blackListValidation.validate(person))
                .thenReturn(List.of());
        when(blacklistChecker.isBlacklisted(command))
                .thenReturn(person);

        BlacklistCoreResult expectedResult = blacklistService.checkPersonFromBlacklist(command);

        assertEquals(actualResult.getPerson(), expectedResult.getPerson());
        verify(blackListValidation).validate(person);
        verify(blacklistChecker).isBlacklisted(command);
    }

    @Test
    void shouldReturnErrorResponse_whenPersonIsBlacklisted() {
        PersonDTO person = new PersonDTO();
        ValidationErrorDTO validationError = new ValidationErrorDTO("ERROR_CODE","TEST");
        BlacklistCoreResult actualResult = new BlacklistCoreResult(List.of(validationError));
        when(command.getPerson()).thenReturn(person);
        when(blackListValidation.validate(person))
                .thenReturn(List.of(validationError));

        BlacklistCoreResult expectedResult = blacklistService.checkPersonFromBlacklist(command);

        assertEquals(actualResult.getErrors(), expectedResult.getErrors());
        verify(blackListValidation).validate(person);
    }
}