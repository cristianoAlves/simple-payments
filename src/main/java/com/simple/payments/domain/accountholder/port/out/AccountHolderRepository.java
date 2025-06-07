package com.simple.payments.domain.accountholder.port.out;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import java.util.Optional;

public interface AccountHolderRepository {

    Optional<EntityAccountHolder> getById(Long id);

    EntityAccountHolder saveAccountHolder(EntityAccountHolder entityAccountHolder);

}
