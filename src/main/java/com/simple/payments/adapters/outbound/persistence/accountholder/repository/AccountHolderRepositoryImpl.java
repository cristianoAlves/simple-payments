package com.simple.payments.adapters.outbound.persistence.accountholder.repository;

import com.simple.payments.domain.accountholder.port.out.AccountHolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountHolderRepositoryImpl implements AccountHolderRepository {

    private final AccountHolderSpringJpaRepository jpaRepository;
}
