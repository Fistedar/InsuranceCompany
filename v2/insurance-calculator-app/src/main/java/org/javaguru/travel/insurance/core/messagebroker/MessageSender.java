package org.javaguru.travel.insurance.core.messagebroker;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;

public interface MessageSender {
    void send(AgreementDTO agreementDTO);
}
