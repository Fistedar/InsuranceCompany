package org.fistedar.black.list.rest;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.dto.BlackListRequest;
import org.fistedar.black.list.dto.BlackListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blacklist/person/check")
@RequiredArgsConstructor
public class BlackListController {

    private final RequestResponseLogger logger;

    @GetMapping("/")
    public BlackListResponse getBlackList(@RequestBody final BlackListRequest blackListRequest) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.logRequest(blackListRequest);

        logger.logTime(stopwatch);
        return new BlackListResponse();
    }
}
