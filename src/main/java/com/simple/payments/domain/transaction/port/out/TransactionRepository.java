package com.simple.payments.domain.transaction.port.out;

import com.simple.payments.adapters.outbound.persistence.transaction.entity.EntityTransaction;
import com.simple.payments.domain.transaction.model.Transaction;

public interface TransactionRepository {
    Transaction saveTransaction(EntityTransaction transaction);
}
