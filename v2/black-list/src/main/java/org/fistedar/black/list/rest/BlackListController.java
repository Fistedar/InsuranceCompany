package org.fistedar.black.list.rest;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;
import org.fistedar.black.list.core.api.command.BlacklistCoreResult;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.service.BlacklistService;
import org.fistedar.black.list.dto.BlackListRequest;
import org.fistedar.black.list.dto.BlackListResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blacklist/person/check")
@RequiredArgsConstructor
public class BlackListController {

    private final RequestResponseLogger logger;
    private final BlacklistService service;
    private final MapperDTO mapper;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public BlackListResponse getBlackList(@RequestBody final BlackListRequest blackListRequest) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.logRequest(blackListRequest);
        BlackListResponse blackListResponse = checkPerson(blackListRequest);
        logger.logResponse(blackListResponse);
        logger.logTime(stopwatch);
        return blackListResponse;
    }

    private BlackListResponse checkPerson(final BlackListRequest blackListRequest) {
        PersonDTO personDTO = mapper.mapRequestToPerson(blackListRequest);
        BlacklistCoreCommand command = new BlacklistCoreCommand(personDTO);
        BlacklistCoreResult result = service.checkPersonFromBlacklist(command);
        return mapper.mapperDTO(result);
    }
}
