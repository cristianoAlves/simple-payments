package com.simple.payments.application.service.accountholder;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.adapters.outbound.persistence.accountholder.mapper.AccountHolderMapper;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import com.simple.payments.domain.accountholder.port.out.AccountHolderRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountHolderService implements AccountHolderUseCase {

    private final AccountHolderRepository repository;
    private final AccountHolderMapper accountHolderMapper;

    @Override
    public void validateTransaction(final AccountHolder accountHolder, final BigDecimal amount) {
        validateAccountHolderType(accountHolder);
        validateAccountHolderBalance(accountHolder, amount);
    }

    @Override
    public void saveAccountHolder(final AccountHolder accountHolder) {
        log.info("Saving AccountHolder [{}]", accountHolder);
        repository.saveAccountHolder(accountHolderMapper.to(accountHolder));
    }

    @Override
    public AccountHolder getAccountHolder(final Long id) {
        log.info("Getting account holder using id [{}]", id);
        EntityAccountHolder entityAccountHolder = repository.getById(id).orElseThrow(() -> {
            log.error("AccountHolder with id [{}] was not found.", id);
            return new RuntimeException(String.format("AccountHolder with id %s was not found.", id));
        });

        return accountHolderMapper.from(entityAccountHolder);
    }

    private void validateAccountHolderBalance(final AccountHolder accountHolder, BigDecimal amount) {
        log.info("Validating AccountHolder balance [{}]", accountHolder);
        if (accountHolder.getBalance().compareTo(amount) < 0) {
            log.error("This account holder {} has not enough balance {}", accountHolder.getId(), accountHolder.getBalance());
            throw new RuntimeException(String.format("Account Holder %s has not enough balance.", accountHolder));
        }
    }

    private void validateAccountHolderType(final AccountHolder accountHolder) {
        log.info("Validating AccountHolder type [{}]", accountHolder);
        if (!accountHolder.getType().isAbleToSendTransactions()) {
            log.error("{} account holder type cannot send transactions", accountHolder.getType());
            throw new RuntimeException(String.format("Account Holder type %s cannot send transactions.", accountHolder.getType()));
        }
    }
}
