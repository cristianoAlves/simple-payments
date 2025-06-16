package com.simple.payments;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.simple.payments.application.PaymentsApplication.class)
@Getter
public abstract class BaseTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

}
