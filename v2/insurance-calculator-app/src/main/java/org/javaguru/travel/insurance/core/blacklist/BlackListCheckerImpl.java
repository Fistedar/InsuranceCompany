package org.javaguru.travel.insurance.core.blacklist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListRequest;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
class BlackListCheckerImpl implements BlackListChecker {

    private final RestTemplate restTemplate;

    @Override
    public boolean checkBlackList(PersonDTO person) {
        try {
            BlackListResponse response = restTemplate.postForObject(
                    "http://black-list-app:8081/blacklist/person/check/",
                    mapPersonDtoToBlackListRequest(person), BlackListResponse.class);
            return response!=null && response.getBlackListed();
        } catch (ResourceAccessException e) {
            log.warn("BlackList app unavailable, proceeding without check");
            return false;
        } catch (HttpClientErrorException e) {
            log.error("Client error when calling blacklist service: {}", e.getMessage());
            return false;
        }
    }

    private BlackListRequest mapPersonDtoToBlackListRequest(PersonDTO personDTO) {
        return BlackListRequest.builder()
                .personFirstName(personDTO.getPersonFirstName())
                .personLastName(personDTO.getPersonLastName())
                .personCode(personDTO.getPersonCode())
                .build();
    }
}