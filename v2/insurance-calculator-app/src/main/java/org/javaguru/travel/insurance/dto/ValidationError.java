package org.javaguru.travel.insurance.dto;

import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:errorCodes.properties")
public record ValidationError(
        String errorCode,
        String description
) {
}
