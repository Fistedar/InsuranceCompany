package org.javaguru.travel.insurance.rest.v2;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.service.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v2.AgreementMapperV2;
import org.javaguru.travel.insurance.dto.v2.ResponseMapperV2;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/v2")
@RequiredArgsConstructor
public class TravelCalculatePremiumControllerV2 {

    private final TravelCalculatePremiumService calculatePremiumService;
    private final RequestResponseLoggerV2 logger;
    private final AgreementMapperV2 agreementMapperV2;
    private final ResponseMapperV2 responseMapperV2;


    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public TravelCalculatePremiumResponseV2 calculatePremium(@RequestBody TravelCalculatePremiumRequestV2 request) {
        Stopwatch sw = Stopwatch.createStarted();
        logger.logRequest(request);
        TravelCalculatePremiumResponseV2 response = convertCoreResult(request);
        logger.logResponse(response);
        logger.logTime(sw);
        return response;
    }

    private TravelCalculatePremiumResponseV2 convertCoreResult(TravelCalculatePremiumRequestV2 request) {
        AgreementDTO agreementDTO = agreementMapperV2.buildAgreementDTO(request);
        TravelCalculatePremiumCoreCommand command = new TravelCalculatePremiumCoreCommand(agreementDTO);
        TravelCalculatePremiumCoreResult result = calculatePremiumService.calculatePremium(command);
        return responseMapperV2.buildResponse(result);
    }


}