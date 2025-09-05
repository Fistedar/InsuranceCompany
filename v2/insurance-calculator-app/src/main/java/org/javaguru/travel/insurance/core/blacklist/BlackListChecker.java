package org.javaguru.travel.insurance.core.blacklist;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;

public interface BlackListChecker {
    boolean checkBlackList(PersonDTO person);
}
