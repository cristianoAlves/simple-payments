package com.simple.payments.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.simple.payments")
@EnableJpaRepositories(basePackages = "com.simple.payments.adapters.outbound.persistence")
@EntityScan(basePackages = "com.simple.payments.adapters.outbound.persistence")
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class PaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsApplication.class, args);
    }

}
