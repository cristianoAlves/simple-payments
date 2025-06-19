package com.simple.payments.adapters.outbound.persistence.accountholder.mapper;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.config.MapperConfiguration;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public abstract class AccountHolderMapper {

    @Mapping(target = "debit", ignore = true)
    @Mapping(target = "credit", ignore = true)
    public abstract AccountHolder fromEntity(EntityAccountHolder entity);

    @InheritInverseConfiguration
    public abstract EntityAccountHolder toEntity(AccountHolder accountHolder);
}
