package com.simple.payments.application.service.accountholder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.simple.payments.Fixtures.AccountHolderFixture;
import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.adapters.outbound.persistence.accountholder.mapper.AccountHolderMapper;
import com.simple.payments.application.service.BaseServiceTest;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.out.AccountHolderRepository;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

class AccountHolderServiceTest extends BaseServiceTest {

    @Mock
    private AccountHolderRepository repository;

    private AccountHolderService service;

    @BeforeEach
    void setUp() {
        service = new AccountHolderService(repository, getMapper());
    }

    @Test
    void validateTransactionOK() {
        assertDoesNotThrow(() -> service.validateTransaction(AccountHolderFixture.createAccountHolder(AccountHolderType.REGULAR), new BigDecimal(10L)));
    }

    @Test
    void validateTransactionShouldFailWhenTypeIsNotAllowed() {
        try {
            service.validateTransaction(AccountHolderFixture.createAccountHolder(AccountHolderType.MERCHANT), new BigDecimal(10L));
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isEqualTo("Account Holder type MERCHANT cannot send transactions.");
        }
    }

    @Test
    void validateTransactionShouldFailWhenAccountHolderDoesNotHaveEnoughBalance() {
        try {
            service.validateTransaction(AccountHolderFixture.createAccountHolder(AccountHolderType.REGULAR), new BigDecimal(1000L));
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).contains("has not enough balance.");
        }
    }

    @Test
    void saveAccountHolderOk() {
        AccountHolder expectedAccountHolder = AccountHolderFixture.createAccountHolder(10L, AccountHolderType.REGULAR);
        EntityAccountHolder entityAccountHolder = createEntityFromModel(expectedAccountHolder);

        Mockito.when(repository.saveAccountHolder(any(EntityAccountHolder.class))).thenReturn(entityAccountHolder);
        AccountHolder saveAccountHolder = service.saveAccountHolder(expectedAccountHolder);

        validateAccountHolder(saveAccountHolder, expectedAccountHolder);
    }

    @Test
    void saveAllAccountHolders() {
        List<AccountHolder> accountList = createListOfAccountHolders();

        Mockito.doNothing().when(repository).saveAllAccountHolders(Mockito.anyList());

        service.saveAllAccountHolders(accountList);
        verify(repository).saveAllAccountHolders(Mockito.anyList());
    }

    @Test
    void getAccountHolder() {
        AccountHolder expectedAccountHolder = AccountHolderFixture.createAccountHolder(10L, AccountHolderType.REGULAR);
        EntityAccountHolder entityAccountHolder = createEntityFromModel(expectedAccountHolder);

        Mockito.when(repository.getById(10L)).thenReturn(Optional.of(entityAccountHolder));
        AccountHolder savedAccountHolder = service.getAccountHolder(10L);
        validateAccountHolder(savedAccountHolder, expectedAccountHolder);
    }

    @Test
    void getNotFoundAccountHolder() {
        Mockito.when(repository.getById(10L)).thenReturn(Optional.empty());
        try {
            service.getAccountHolder(10L);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).contains("AccountHolder with id 10 was not found.");
        }
    }

    @Test
    void getAllAccountHolders() {
        List<AccountHolder> expectedAccountHolders = createListOfAccountHolders();
        Mockito.when(repository.getAllAccountHolders()).thenReturn(createListOfEntityAccountHolder(expectedAccountHolders));
        validateAccountHolders(service.getAllAccountHolders(), expectedAccountHolders);
    }

    private void validateAccountHolder(AccountHolder saveAccountHolder, AccountHolder expectedAccountHolder) {
        assertThat(saveAccountHolder).isNotNull();
        assertThat(saveAccountHolder.id()).isNotNull();
        assertThat(saveAccountHolder.type()).isEqualTo(expectedAccountHolder.type());
        assertThat(saveAccountHolder.balance()).isEqualTo(expectedAccountHolder.balance());
        assertThat(saveAccountHolder.name()).isEqualTo(expectedAccountHolder.name());
        assertThat(saveAccountHolder.document()).isEqualTo(expectedAccountHolder.document());
    }

    private void validateAccountHolders(Collection<AccountHolder> actual, Collection<AccountHolder> expected) {
        assertThat(actual)
            .hasSize(2)
            .containsExactlyInAnyOrderElementsOf(expected);
    }

    private List<AccountHolder> createListOfAccountHolders() {
        AccountHolder expectedAccountHolder1 = AccountHolderFixture.createAccountHolder(10L, AccountHolderType.REGULAR);
        AccountHolder expectedAccountHolder2 = AccountHolderFixture.createAccountHolder(20L, AccountHolderType.MERCHANT);
        return List.of(expectedAccountHolder1, expectedAccountHolder2);
    }

    private List<EntityAccountHolder> createListOfEntityAccountHolder(Collection<AccountHolder> accountHolders) {
        return accountHolders.stream()
            .map(this::createEntityFromModel)
            .toList();
    }
}