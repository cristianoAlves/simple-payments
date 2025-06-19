package com.simple.payments.domain.transaction.port.out;

import com.simple.payments.domain.transaction.model.Transaction;

public interface TransactionRepository {

    Transaction saveTransaction(Transaction transaction);
}
