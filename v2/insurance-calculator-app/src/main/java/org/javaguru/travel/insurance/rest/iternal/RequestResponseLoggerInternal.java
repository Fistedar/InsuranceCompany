package org.javaguru.travel.insurance.rest.iternal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RequestResponseLoggerInternal {

    private final ObjectMapper mapper;
    private final Logger log = LoggerFactory.getLogger("REQUEST_RESPONSE_LOGGER");

    void logResponse(TravelGetAgreementResponse response) {
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

    void logRequest(String uuid) {
        log.info("REQUEST: agreement uuid = {}", uuid);
    }
}
