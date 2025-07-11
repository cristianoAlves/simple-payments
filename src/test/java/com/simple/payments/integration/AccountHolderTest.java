package com.simple.payments.integration;

import static com.simple.payments.Fixtures.AccountHolderFixture.createAccountHolder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.simple.payments.BaseTest;
import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.adapters.outbound.persistence.accountholder.repository.AccountHolderSpringJpaRepository;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class AccountHolderTest extends BaseTest {

    @Autowired
    private AccountHolderSpringJpaRepository repository;

    @Test
    void addNewUser() {
        AccountHolder accountHolder = createAccountHolder(AccountHolderType.REGULAR);

        var response = getTestRestTemplate().postForEntity("/accounts", accountHolder, AccountHolder.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();

        Optional<EntityAccountHolder> byId = repository.findById(response.getBody().id());
        assertThat(byId).isNotEmpty();
        assertThat(byId.get().getBalance()).isEqualTo(accountHolder.balance());
        assertThat(byId.get().getDocument()).isEqualTo(accountHolder.document());
        assertThat(byId.get().getType()).isEqualTo(accountHolder.type());
        assertThat(byId.get().getName()).isEqualTo(accountHolder.name());
        assertThat(byId.get().getEmail()).isEqualTo(accountHolder.email());
    }
}
