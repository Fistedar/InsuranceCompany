package org.javaguru.travel.insurance.core.exceptions.messagebroker;

public class AgreementProcessingException extends RuntimeException {
    public AgreementProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}