package com.simple.payments.domain.transaction.port.in;

import com.simple.payments.domain.transaction.model.Transaction;

public interface TransactionUseCase {

    Transaction addTransaction(Transaction transaction);
}
