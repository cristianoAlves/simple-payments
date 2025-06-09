package com.simple.payments.adapters.outbound.persistence.accountholder.repository;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.domain.accountholder.port.out.AccountHolderRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountHolderRepositoryImpl implements AccountHolderRepository {

    private final AccountHolderSpringJpaRepository jpaRepository;

    @Override
    public Optional<EntityAccountHolder> getById(final Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public EntityAccountHolder saveAccountHolder(final EntityAccountHolder entityAccountHolder) {
        return jpaRepository.save(entityAccountHolder);
    }

    @Override
    public List<EntityAccountHolder> getAllAccountHolders() {
        return jpaRepository.findAll();
    }
}
