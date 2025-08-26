package org.javaguru.travel.insurance.rest.iternal;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.service.TravelGetAgreementInternal;
import org.javaguru.travel.insurance.dto.internal.ResponseMapperInternal;
import org.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/internal/agreement")
@RequiredArgsConstructor
public class TravelGetAgreementRestController {

    private final TravelGetAgreementInternal getAgreementInternal;
    private final RequestResponseLoggerInternal logger;
    private final ResponseMapperInternal responseMapper;

    @GetMapping(path = "/{uuid}",
            produces = "application/json")
    public TravelGetAgreementResponse getTravelAgreement(@PathVariable("uuid") String uuid) {
        Stopwatch sw = Stopwatch.createStarted();
        TravelGetAgreementResponse response = createResponse(uuid);
        logger.logTime(sw);
        return response;
    }

    private TravelGetAgreementResponse createResponse(String uuid) {
        logger.logRequest(uuid);
        TravelGetAgreementCoreCommand command = new TravelGetAgreementCoreCommand(uuid);
        TravelGetAgreementCoreResult result = getAgreementInternal.getAgreementInternal(command);
        TravelGetAgreementResponse response = responseMapper.createResponse(result);
        logger.logResponse(response);
        return response;
    }
}
