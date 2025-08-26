package org.javaguru.travel.insurance.core.service;

import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;

public interface TravelGetAgreementInternal {
    TravelGetAgreementCoreResult getAgreementInternal(TravelGetAgreementCoreCommand command);
}
