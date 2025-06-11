package com.simple.payments.integration;

import static com.simple.payments.integration.Fixture.createAccountHolder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.simple.payments.adapters.inbound.rest.ErrorDto;
import com.simple.payments.adapters.inbound.rest.TransactionDTO;
import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import com.simple.payments.domain.transaction.model.Transaction;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsTest {

    private static final BigDecimal ACCOUNT_INITIAL_BALANCE = new BigDecimal(100);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AccountHolderUseCase accountHolderUseCase;

    @MockitoBean
    private RestTemplate restTemplateMock;

    @Test
    void triggerNewTransactionOk() {
        //create accounts
        AccountHolder sender = accountHolderUseCase.saveAccountHolder(createAccountHolder(AccountHolderType.REGULAR));
        AccountHolder receiver = accountHolderUseCase.saveAccountHolder(createAccountHolder(AccountHolderType.REGULAR));

        //create payload
        TransactionDTO payload = createPayload(sender, receiver);

        //Mock RestTemplate
        mockAuthorization("success");

        var response = testRestTemplate.postForEntity("/transactions", payload, Transaction.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
        validateTransaction(response.getBody(), sender, receiver, payload.value());
        validateAccountsBalanceAfterTransaction(response.getBody(), payload.value());
    }

    @Test
    void triggerNewTransactionIsNotAuthorized() {
        //create accounts
        AccountHolder sender = accountHolderUseCase.saveAccountHolder(createAccountHolder(AccountHolderType.REGULAR));
        AccountHolder receiver = accountHolderUseCase.saveAccountHolder(createAccountHolder(AccountHolderType.REGULAR));

        //create payload
        TransactionDTO payload = createPayload(sender, receiver);

        //Mock RestTemplate
        mockAuthorization("");

        var response = testRestTemplate.postForEntity("/transactions", payload, ErrorDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errorCode()).isEqualTo(400);
        assertThat(response.getBody().errorMessage()).isEqualTo("This transaction is not authorized.");
    }

    @Test
    void triggerNewTransactionWhenSenderIsNotAllowed() {
        //create accounts
        AccountHolder sender = accountHolderUseCase.saveAccountHolder(createAccountHolder(AccountHolderType.MERCHANT));
        AccountHolder receiver = accountHolderUseCase.saveAccountHolder(createAccountHolder(AccountHolderType.REGULAR));

        //create payload
        TransactionDTO payload = createPayload(sender, receiver);

        //Mock RestTemplate
        mockAuthorization("success");

        var response = testRestTemplate.postForEntity("/transactions", payload, ErrorDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errorCode()).isEqualTo(400);
        assertThat(response.getBody().errorMessage()).isEqualTo("Account Holder type MERCHANT cannot send transactions.");
    }

    private void mockAuthorization(String status) {
        Map<String, String> body = Map.of("status", status);
        ResponseEntity<Map> mockResponse = new ResponseEntity<>(body, HttpStatus.OK);
        when(restTemplateMock.getForEntity(anyString(), eq(Map.class))).thenReturn(mockResponse);
    }

    private static TransactionDTO createPayload(AccountHolder sender, AccountHolder receiver) {
        return new TransactionDTO(new BigDecimal(10), sender.id(), receiver.id());
    }

    private void validateAccountsBalanceAfterTransaction(Transaction tx, BigDecimal value) {
        AccountHolder sender = accountHolderUseCase.getAccountHolder(tx.getSender().id());
        AccountHolder receiver = accountHolderUseCase.getAccountHolder(tx.getReceiver().id());

        assertThat(sender.balance()).isEqualTo(ACCOUNT_INITIAL_BALANCE.subtract(value));
        assertThat(receiver.balance()).isEqualTo(ACCOUNT_INITIAL_BALANCE.add(value));
    }

    private void validateTransaction(Transaction tx, AccountHolder sender, AccountHolder receiver, BigDecimal amount) {
        assertThat(tx.getAmount()).isEqualTo(amount);
        assertThat(tx.getReceiver().id()).isEqualTo(receiver.id());
        assertThat(tx.getSender().id()).isEqualTo(sender.id());
        assertThat(tx.getId()).isNotNull();
    }
}
