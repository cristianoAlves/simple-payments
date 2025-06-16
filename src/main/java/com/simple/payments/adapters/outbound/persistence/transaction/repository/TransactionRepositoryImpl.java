package com.simple.payments.adapters.outbound.persistence.transaction.repository;

import com.simple.payments.adapters.outbound.persistence.transaction.entity.EntityTransaction;
import com.simple.payments.adapters.outbound.persistence.transaction.mapper.TransactionMapper;
import com.simple.payments.domain.transaction.model.Transaction;
import com.simple.payments.domain.transaction.port.out.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionSpringJpaRepository jpaRepository;
    private final TransactionMapper mapper;

    @Override
    public Transaction saveTransaction(EntityTransaction transaction) {
        return mapper.fromEntity(jpaRepository.save(transaction));
    }
}
