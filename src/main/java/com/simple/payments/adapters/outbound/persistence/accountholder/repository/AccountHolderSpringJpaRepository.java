package com.simple.payments.adapters.outbound.persistence.accountholder.repository;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderSpringJpaRepository extends JpaRepository<EntityAccountHolder, Long> {

}
