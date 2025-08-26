package org.javaguru.travel.insurance.core.validation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationErrorFactory {

    private final Environment environment;

    public ValidationError buildError(String errorCode) {
        String errorMessage = getErrorMessageFromProperty(errorCode);
        return new ValidationError(errorCode, errorMessage);
    }

    public ValidationError buildError(String errorCode, Placeholder placeholders) {
        return new ValidationError(errorCode, getErrorMessageFromPlaceholder(errorCode, placeholders));
    }

    private String getErrorMessageFromProperty(String errorCode) {
        return environment.getProperty(errorCode);
    }

    private String getErrorMessageFromPlaceholder(String errorCode, Placeholder placeholder) {
        String errorDescription = getErrorMessageFromProperty(errorCode);
        String placeholderToReplace = "{" + placeholder.getPlaceholderName() + "}";
        errorDescription = errorDescription.replace(placeholderToReplace, placeholder.getPlaceholderValue());
        return errorDescription;
    }
}
