package org.javaguru.travel.insurance.core.blacklist;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListRequest;
import org.javaguru.travel.insurance.core.blacklist.dto.BlackListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlackListCheckerImplTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private Logger log;
    @Mock
    private BlackListResponse response;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private BlackListCheckerImpl blackListChecker;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(blackListChecker, "blackListUrl", "Test");
    }

    @Test
    void shouldReturnTrue_whenPersonOnBlackList() {
        when(restTemplate.postForObject(eq("Test"), any(BlackListRequest.class), eq(BlackListResponse.class)))
                .thenReturn(response);
        when(response.getBlackListed()).thenReturn(true);

        assertTrue(blackListChecker.checkBlackList(personDTO));
    }

    @Test
    void shouldThrowException_whenAppUnavailable() {
        when(restTemplate.postForObject(eq("Test"), any(BlackListRequest.class), eq(BlackListResponse.class)))
                .thenThrow(ResourceAccessException.class);

        assertFalse(blackListChecker.checkBlackList(personDTO));
        verify(log).warn("BlackList app unavailable, proceeding without check");
    }

    @Test
    void shouldThrowException_whenClientError() {
        when(restTemplate.postForObject(eq("Test"), any(BlackListRequest.class), eq(BlackListResponse.class)))
                .thenThrow(HttpClientErrorException.class);

        assertFalse(blackListChecker.checkBlackList(personDTO));
    }
}