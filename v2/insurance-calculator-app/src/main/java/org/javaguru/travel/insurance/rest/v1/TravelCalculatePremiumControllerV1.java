package org.javaguru.travel.insurance.rest.v1;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.service.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.AgreementMapperV1;
import org.javaguru.travel.insurance.dto.v1.ResponseMapperV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/v1")
@RequiredArgsConstructor
public class TravelCalculatePremiumControllerV1 {

    private final ResponseMapperV1 responseMapper;
    private final AgreementMapperV1 agreementMapper;
    private final TravelCalculatePremiumService calculatePremiumService;
    private final RequestResponseLoggerV1 logger;


    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public TravelCalculatePremiumResponseV1 calculatePremium(@RequestBody TravelCalculatePremiumRequestV1 request) {
        Stopwatch sw = Stopwatch.createStarted();
        logger.logRequest(request);
        TravelCalculatePremiumResponseV1 response = convertCoreResult(request);
        logger.logResponse(response);
        logger.logTime(sw);
        return response;
    }

    private TravelCalculatePremiumResponseV1 convertCoreResult(TravelCalculatePremiumRequestV1 request) {
        AgreementDTO agreementDTO = agreementMapper.buildAgreementDTO(request);
        TravelCalculatePremiumCoreCommand command = new TravelCalculatePremiumCoreCommand(agreementDTO);
        TravelCalculatePremiumCoreResult result = calculatePremiumService.calculatePremium(command);
        return responseMapper.buildResponse(result);
    }

}