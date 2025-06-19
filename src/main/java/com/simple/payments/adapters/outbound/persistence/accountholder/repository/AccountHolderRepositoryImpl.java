package com.simple.payments.adapters.outbound.persistence.accountholder.repository;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.adapters.outbound.persistence.accountholder.mapper.AccountHolderMapper;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.out.AccountHolderRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountHolderRepositoryImpl implements AccountHolderRepository {

    private final AccountHolderSpringJpaRepository jpaRepository;
    private final AccountHolderMapper mapper;

    @Override
    public Optional<AccountHolder> getById(final Long id) {
        return jpaRepository.findById(id)
            .map(mapper::fromEntity);
    }

    @Override
    public AccountHolder saveAccountHolder(final AccountHolder accountHolder) {
        return mapper.fromEntity(jpaRepository.save(mapper.toEntity(accountHolder)));
    }

    @Override
    public List<AccountHolder> getAllAccountHolders() {
        return jpaRepository.findAll().stream()
            .map(mapper::fromEntity)
            .toList();
    }

    @Override
    public void saveAllAccountHolders(Collection<AccountHolder> accountHolders) {
        List<EntityAccountHolder> entityAccountHolders = accountHolders.stream()
            .map(mapper::toEntity)
            .toList();
        jpaRepository.saveAll(entityAccountHolders);
    }
}
