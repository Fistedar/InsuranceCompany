package org.javaguru.travel.insurance.core.exceptions.messagebroker;

public class KafkaMessageException extends RuntimeException {
    public KafkaMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
