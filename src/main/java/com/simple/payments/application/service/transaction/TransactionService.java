package com.simple.payments.application.service.transaction;

import com.simple.payments.adapters.outbound.persistence.transaction.mapper.TransactionMapper;
import com.simple.payments.application.service.rest.RestService;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import com.simple.payments.domain.transaction.model.Transaction;
import com.simple.payments.domain.transaction.port.in.TransactionUseCase;
import com.simple.payments.domain.transaction.port.out.TransactionRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService implements TransactionUseCase {

    private final AccountHolderUseCase accountHolderUseCase;
    private final TransactionRepository transactionRepository;
    private final RestService restService;
    private final TransactionMapper mapper;

    @Override
    @Transactional
    public Transaction addTransaction(final Transaction transaction) {
        log.info("Adding a new transaction of [{}] from [{}] to [{}]", transaction.getAmount(), transaction.getSender(), transaction.getReceiver());
        restService.validateAuthorization();

        AccountHolder accountHolderSender = accountHolderUseCase.getAccountHolder(transaction.getSender().getId());
        AccountHolder accountHolderReceiver = accountHolderUseCase.getAccountHolder(transaction.getReceiver().getId());

        accountHolderUseCase.validateTransaction(accountHolderSender, transaction.getAmount());

        //update balance
        accountHolderSender.setBalance(accountHolderSender.getBalance().subtract(transaction.getAmount()));
        accountHolderReceiver.setBalance(accountHolderReceiver.getBalance().add(transaction.getAmount()));

        accountHolderUseCase.saveAccountHolder(accountHolderSender);
        accountHolderUseCase.saveAccountHolder(accountHolderReceiver);

        Transaction newTransaction = transactionRepository.saveTransaction(mapper.to(createNewTransaction(transaction, accountHolderReceiver, accountHolderSender)));

        log.info("Done saving transaction.");
        return newTransaction;
    }

    private Transaction createNewTransaction(Transaction transaction, AccountHolder accountHolderReceiver, AccountHolder accountHolderSender) {
        return Transaction.builder()
            .sender(accountHolderSender)
            .receiver(accountHolderReceiver)
            .timestamp(LocalDateTime.now())
            .amount(transaction.getAmount())
            .build();
    }

}
