package com.simple.payments.adapters.inbound.rest;

import com.simple.payments.adapters.outbound.persistence.transaction.mapper.TransactionMapper;
import com.simple.payments.domain.transaction.model.Transaction;
import com.simple.payments.domain.transaction.port.in.TransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionUseCase transactionUseCase;
    private final TransactionMapper mapper;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO body) {
        Transaction newTransaction = transactionUseCase.addTransaction(mapper.from(body));
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }
}
