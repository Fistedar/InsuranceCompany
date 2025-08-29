package org.fistedar.black.list.core.validation;

import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ValidationErrorFactory {

    private final Environment environment;

    ValidationErrorDTO buildError(String errorCode){
        String message = getMessage(errorCode);
        return new ValidationErrorDTO(errorCode,message);
    }

    private String getMessage(String errorCode){
        return environment.getProperty(errorCode);
    }
}
