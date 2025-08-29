package org.fistedar.black.list.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.dto.BlackListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RequestResponseLogger {

    private final Logger log = LoggerFactory.getLogger("REQUEST_RESPONSE_LOGGER");
    private final ObjectMapper objectMapper;

    void logResponse(BlackListRequest request) {
        try {
            log.info("Response: {}", objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error("Error to convert request to JSON", e);
        }
    }

    void logTime(Stopwatch stopwatch) {
        stopwatch.stop();
        log.info("Request processing time (ms):{}", stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }

    void logRequest(BlackListRequest request) {
        try {
            log.info("Request: {}", objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error("Error to convert request to JSON", e);
        }
    }
}
