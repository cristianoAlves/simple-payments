package com.simple.payments.application.service.rest;

import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestService {

    private final RestTemplate restTemplate;

    public void validateAuthorization() {
        log.info("Validating authorization");
        ResponseEntity<Map> authResponse = null;
        try {
            authResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        } catch (RestClientException e) {
            throw new RuntimeException("This transaction was not authorized.");
        }

        if (authResponse.getStatusCode() == HttpStatus.OK && Objects.nonNull(authResponse.getBody())) {
            String status = (String) authResponse.getBody().get("status");
            if ("success".equalsIgnoreCase(status)) {
                log.info("Authorized");
                return;
            }
        }
        log.error("Unauthorized transaction");
        throw new RuntimeException("This transaction is not authorized.");
    }
}
