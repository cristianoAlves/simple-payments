package com.simple.payments.adapters.outbound.persistence.transaction.repository;

import com.simple.payments.domain.transaction.port.out.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionSpringJpaRepository jpaRepository;
}
