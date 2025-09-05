package org.javaguru.travel.insurance.core.blacklist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BlackListConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
