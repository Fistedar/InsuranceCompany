package org.fistedar.black.list.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestResponseLogger {

    private final Logger log = LoggerFactory.getLogger("REQUEST_RESPONSE_LOGGER");
    private final ObjectMapper objectMapper;

    void logResponse(){

    }
}
