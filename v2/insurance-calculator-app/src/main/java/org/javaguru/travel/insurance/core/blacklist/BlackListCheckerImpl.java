package org.javaguru.travel.insurance.core.blacklist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListRequest;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListResponse;
import org.javaguru.travel.insurance.core.exceptions.blacklist.BlackListClientException;
import org.javaguru.travel.insurance.core.exceptions.blacklist.BlackListUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile({"mysql-container","mysql-local"})
class BlackListCheckerImpl implements BlackListChecker {

    private final RestTemplate restTemplate;
    @Value("${url.black.list}")
    private String blackListUrl;

    @Override
    public boolean checkBlackList(PersonDTO person) {
        try {
            BlackListRequest request = mapPersonDtoToBlackListRequest(person);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<BlackListRequest> entity = new HttpEntity<>(request,header);
            BlackListResponse response = restTemplate.postForObject(
                    blackListUrl,
                    entity,
                    BlackListResponse.class);
            return response!=null && response.getBlackListed();
        } catch (ResourceAccessException e) {
            throw new BlackListUnavailableException("BlackList app unavailable, proceeding without check. " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new BlackListClientException("Client error when calling blacklist service: {}" + e.getMessage());
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