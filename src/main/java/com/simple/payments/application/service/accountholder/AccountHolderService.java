package com.simple.payments.application.service.accountholder;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import com.simple.payments.domain.accountholder.port.out.AccountHolderRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountHolderService implements AccountHolderUseCase {

    private final AccountHolderRepository repository;

    @Override
    public void validateTransaction(final AccountHolder accountHolder, final BigDecimal amount) {
        validateAccountHolderType(accountHolder);
        validateAccountHolderBalance(accountHolder, amount);
    }

    @Override
    public AccountHolder saveAccountHolder(final AccountHolder accountHolder) {
        log.info("Saving AccountHolder [{}]", accountHolder);
        return repository.saveAccountHolder(accountHolder);
    }

    @Override
    public void saveAllAccountHolders(Collection<AccountHolder> accountHolders) {
        log.info("Saving all AccountHolder [{}]", Arrays.toString(accountHolders.toArray()));
        repository.saveAllAccountHolders(accountHolders);
    }

    @Override
    public AccountHolder getAccountHolder(final Long id) {
        log.info("Getting account holder using id [{}]", id);

        return repository.getById(id).orElseThrow(() -> {
            log.error("AccountHolder with id [{}] was not found.", id);
            return new RuntimeException(String.format("AccountHolder with id %s was not found.", id));
        });

    }

    @Override
    public Collection<AccountHolder> getAllAccountHolders() {
        log.info("Getting all Account Holders");
        return repository.getAllAccountHolders();
    }

    private void validateAccountHolderBalance(final AccountHolder accountHolder, BigDecimal amount) {
        log.info("Validating AccountHolder balance [{}]", accountHolder);
        if (accountHolder.balance().compareTo(amount) < 0) {
            log.error("This account holder {} has not enough balance {}", accountHolder.id(), accountHolder.balance());
            throw new RuntimeException(String.format("Account Holder %s has not enough balance.", accountHolder));
        }
    }

    private void validateAccountHolderType(final AccountHolder accountHolder) {
        log.info("Validating AccountHolder type [{}]", accountHolder);
        if (!accountHolder.type().isAbleToSendTransactions()) {
            log.error("{} account holder type cannot send transactions", accountHolder.type());
            throw new RuntimeException(String.format("Account Holder type %s cannot send transactions.", accountHolder.type()));
        }
    }
}
