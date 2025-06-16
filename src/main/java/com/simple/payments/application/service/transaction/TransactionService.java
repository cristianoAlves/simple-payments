package com.simple.payments.application.service.transaction;

import com.simple.payments.adapters.outbound.persistence.transaction.mapper.TransactionMapper;
import com.simple.payments.application.service.rest.RestService;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import com.simple.payments.domain.transaction.model.Transaction;
import com.simple.payments.domain.transaction.port.in.TransactionUseCase;
import com.simple.payments.domain.transaction.port.out.TransactionRepository;
import java.time.LocalDateTime;
import java.util.List;
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
        log.info("Adding a new transaction of [{}] from [{}] to [{}]", transaction.amount(), transaction.sender(), transaction.receiver());
        restService.validateAuthorization();

        AccountHolder accountHolderSender = accountHolderUseCase.getAccountHolder(transaction.sender().id());
        AccountHolder accountHolderReceiver = accountHolderUseCase.getAccountHolder(transaction.receiver().id());

        accountHolderUseCase.validateTransaction(accountHolderSender, transaction.amount());

        //update balance
        AccountHolder accountHolderSenderWithBalanceUpdated = accountHolderSender.debit(transaction.amount());
        AccountHolder accountHolderReceiverWithBalanceUpdated = accountHolderReceiver.credit(transaction.amount());

        accountHolderUseCase.saveAllAccountHolders(List.of(accountHolderSenderWithBalanceUpdated, accountHolderReceiverWithBalanceUpdated));

        //need to create a new transaction since the transaction received has only the sender/receiver ID's
        Transaction newTransaction = transactionRepository.saveTransaction(mapper.toEntity(createNewTransaction(transaction, accountHolderReceiverWithBalanceUpdated, accountHolderSenderWithBalanceUpdated)));

        log.info("Done saving transaction.");
        return newTransaction;
    }

    private Transaction createNewTransaction(Transaction transaction, AccountHolder accountHolderReceiver, AccountHolder accountHolderSender) {
        return new Transaction(null, transaction.amount(), accountHolderSender, accountHolderReceiver, LocalDateTime.now());
    }

}
