package com.simple.payments.adapters.outbound.persistence.transaction.mapper;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.config.MapperConfiguration;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public abstract class AccountHolderMapper {

    public abstract AccountHolder from(EntityAccountHolder entity);

    public abstract EntityAccountHolder to(AccountHolder accountHolder);
}
