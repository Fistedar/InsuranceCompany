package org.javaguru.travel.insurance.core.exceptions.messagebroker;

public class ResultPublishingException extends RuntimeException {
    public ResultPublishingException(String message, Throwable cause) {
        super(message, cause);
    }
}