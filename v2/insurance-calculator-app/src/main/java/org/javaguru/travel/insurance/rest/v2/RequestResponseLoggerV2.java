package org.javaguru.travel.insurance.rest.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RequestResponseLoggerV2 {

    private final ObjectMapper mapper;
    private final Logger log = LoggerFactory.getLogger("REQUEST_RESPONSE_LOGGER");

    void logResponse(TravelCalculatePremiumResponseV2 response) {
        try {
            log.info("Response : {}", mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            log.error("Error to convert request to JSON", e);
        }
    }

    void logTime(Stopwatch stopwatch) {
        stopwatch.stop();
        log.info("Request processing time (ms):{}", stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }

    void logRequest(TravelCalculatePremiumRequestV2 request) {
        try {
            log.info("Request: {}", mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error("Error to convert request to JSON", e);
        }
    }
}
