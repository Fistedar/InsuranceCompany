package org.javaguru.travel.insurance.rest;

import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/insurance/travel/api")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumController {

    private final TravelCalculatePremiumService calculatePremiumService;
    private static final Logger REQUEST_RESPONSE_LOGGER =
            LoggerFactory.getLogger("REQUEST_RESPONSE_LOGGER");

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public TravelCalculatePremiumResponseV1 calculatePremium(@RequestBody TravelCalculatePremiumRequestV1 request) {
        Stopwatch sw = Stopwatch.createStarted();
        try {
            REQUEST_RESPONSE_LOGGER.info("Request: {}", request);
            TravelCalculatePremiumResponseV1 response = calculatePremiumService.calculatePremium(request);
            REQUEST_RESPONSE_LOGGER.info("Response: {}", response);
            return response;
        } finally {
            sw.stop();
            log.info("Request processing time (ms): {}", sw.elapsed(TimeUnit.MILLISECONDS));
        }
    }

}