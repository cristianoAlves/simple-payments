package com.simple.payments.adapters.outbound.persistence.transaction.mapper;

import com.simple.payments.adapters.inbound.rest.TransactionDTO;
import com.simple.payments.adapters.outbound.persistence.transaction.entity.EntityTransaction;
import com.simple.payments.config.MapperConfiguration;
import com.simple.payments.domain.transaction.model.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public abstract class TransactionMapper {

    @Mapping(target = "amount", source = "value")
    @Mapping(target = "sender.id", source = "payer")
    @Mapping(target = "sender.credit", ignore = true)
    @Mapping(target = "sender.debit", ignore = true)
    @Mapping(target = "receiver.id", source = "payee")
    @Mapping(target = "receiver.credit", ignore = true)
    @Mapping(target = "receiver.debit", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    public abstract Transaction fromDto(TransactionDTO dto);

    @Mapping(target = "sender.credit", ignore = true)
    @Mapping(target = "sender.debit", ignore = true)
    @Mapping(target = "receiver.credit", ignore = true)
    @Mapping(target = "receiver.debit", ignore = true)
    public abstract Transaction fromEntity(EntityTransaction entity);

    @InheritInverseConfiguration(name = "fromEntity")
    public abstract EntityTransaction toEntity(Transaction transaction);
}
