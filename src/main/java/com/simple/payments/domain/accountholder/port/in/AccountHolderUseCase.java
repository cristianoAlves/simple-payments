package com.simple.payments.domain.accountholder.port.in;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.math.BigDecimal;

public interface AccountHolderUseCase {
    void validateTransaction(AccountHolder accountHolder, BigDecimal amount);

    AccountHolder saveAccountHolder(AccountHolder accountHolder);

    AccountHolder getAccountHolder(Long id);

}
