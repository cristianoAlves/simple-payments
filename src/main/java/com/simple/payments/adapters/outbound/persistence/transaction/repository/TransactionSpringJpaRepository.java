package com.simple.payments.adapters.outbound.persistence.transaction.repository;

import com.simple.payments.adapters.outbound.persistence.transaction.entity.EntityTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionSpringJpaRepository extends JpaRepository<EntityTransaction, Long> {

}
