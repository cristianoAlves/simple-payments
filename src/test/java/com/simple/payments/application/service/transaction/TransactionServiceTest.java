package com.simple.payments.application.service.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.simple.payments.Fixtures.AccountHolderFixture;
import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import com.simple.payments.adapters.outbound.persistence.accountholder.mapper.AccountHolderMapper;
import com.simple.payments.adapters.outbound.persistence.transaction.entity.EntityTransaction;
import com.simple.payments.adapters.outbound.persistence.transaction.mapper.TransactionMapper;
import com.simple.payments.application.service.BaseServiceTest;
import com.simple.payments.application.service.accountholder.AccountHolderService;
import com.simple.payments.application.service.rest.RestService;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import com.simple.payments.domain.accountholder.port.out.AccountHolderRepository;
import com.simple.payments.domain.transaction.model.Transaction;
import com.simple.payments.domain.transaction.port.out.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;

class TransactionServiceTest extends BaseServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private RestService restService;

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    private final AccountHolderMapper accountHolderMapper = Mappers.getMapper(AccountHolderMapper.class);
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        AccountHolderUseCase accountHolderUseCase = new AccountHolderService(accountHolderRepository, accountHolderMapper);
        transactionService = new TransactionService(accountHolderUseCase,
            transactionRepository, restService, transactionMapper);
    }

    @Test
    public void addTransactionOK() {
        AccountHolder sender = AccountHolderFixture.createAccountHolder(10L, AccountHolderType.REGULAR);
        AccountHolder receiver = AccountHolderFixture.createAccountHolder(20L, AccountHolderType.REGULAR);

        AccountHolder expectedSender = AccountHolderFixture.createAccountHolder(10L, new BigDecimal(90L), AccountHolderType.REGULAR);
        AccountHolder expectedReceiver = AccountHolderFixture.createAccountHolder(20L, new BigDecimal(110L), AccountHolderType.REGULAR);


        Transaction expectedTransaction = createTransaction(10L, expectedSender, expectedReceiver);

        Mockito.doNothing().when(restService).validateAuthorization();
        when(accountHolderRepository.getById(10L)).thenReturn(Optional.ofNullable(createEntityFromModel(sender)));
        when(accountHolderRepository.getById(20L)).thenReturn(Optional.ofNullable(createEntityFromModel(receiver)));
        Mockito.doNothing().when(accountHolderRepository).saveAllAccountHolders(Mockito.anyList());
        when(transactionRepository.saveTransaction(any(EntityTransaction.class))).thenReturn(expectedTransaction);

        Transaction actualTransaction = transactionService.addTransaction(expectedTransaction);
        validateTransaction(actualTransaction, expectedTransaction);
        validateAccountHoldersBalances(actualTransaction, expectedSender, expectedReceiver);
    }

    @Test
    public void addTransactionWhenSenderIsNotAuthorized() {
        AccountHolder sender = AccountHolderFixture.createAccountHolder(10L, AccountHolderType.MERCHANT);
        AccountHolder receiver = AccountHolderFixture.createAccountHolder(20L, AccountHolderType.REGULAR);
        Transaction expectedTransaction = createTransaction(10L, sender, receiver);

        Mockito.doNothing().when(restService).validateAuthorization();
        when(accountHolderRepository.getById(10L)).thenReturn(Optional.ofNullable(createEntityFromModel(sender)));
        when(accountHolderRepository.getById(20L)).thenReturn(Optional.ofNullable(createEntityFromModel(receiver)));

        try {
            transactionService.addTransaction(expectedTransaction);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isEqualTo("Account Holder type MERCHANT cannot send transactions.");
        }
    }

    @Test
    public void addTransactionWhenAuthorizationFails() {
        AccountHolder sender = AccountHolderFixture.createAccountHolder(10L, AccountHolderType.REGULAR);
        AccountHolder receiver = AccountHolderFixture.createAccountHolder(20L, AccountHolderType.REGULAR);
        Transaction expectedTransaction = createTransaction(10L, sender, receiver);

        Mockito.doThrow(RuntimeException.class).when(restService).validateAuthorization();

        assertThrows(RuntimeException.class, () -> transactionService.addTransaction(expectedTransaction));
    }

    private void validateTransaction(Transaction actualTransaction, Transaction expectedTransaction) {
        assertThat(actualTransaction.id()).isEqualTo(expectedTransaction.id());
        assertThat(actualTransaction.amount()).isEqualTo(expectedTransaction.amount());
        assertThat(actualTransaction.receiver().id()).isEqualTo(expectedTransaction.receiver().id());
        assertThat(actualTransaction.sender().id()).isEqualTo(expectedTransaction.sender().id());
        assertThat(actualTransaction.timestamp()).isNotNull();
    }

    private Transaction createTransaction(Long id, AccountHolder sender, AccountHolder receiver) {
        return new Transaction(id, new BigDecimal(10L), sender, receiver, LocalDateTime.now());
    }

    private void validateAccountHoldersBalances(Transaction actualTransaction, AccountHolder sender, AccountHolder receiver) {
        assertThat(actualTransaction.sender().balance()).isEqualTo(sender.balance());
        assertThat(actualTransaction.receiver().balance()).isEqualTo(receiver.balance());
    }
}