package com.simple.payments.application.service;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import com.simple.payments.adapters.outbound.persistence.accountholder.mapper.AccountHolderMapper;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Getter
public abstract class BaseServiceTest {
    private final AccountHolderMapper mapper = Mappers.getMapper(AccountHolderMapper.class);

    protected EntityAccountHolder createEntityFromModel(AccountHolder expectedAccountHolder) {
        return mapper.to(expectedAccountHolder);
    }

}
