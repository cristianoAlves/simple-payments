package com.simple.payments.domain.accountholder.port.in;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.math.BigDecimal;
import java.util.Collection;

public interface AccountHolderUseCase {
    void validateTransaction(AccountHolder accountHolder, BigDecimal amount);

    AccountHolder saveAccountHolder(AccountHolder accountHolder);

    void saveAllAccountHolders(Collection<AccountHolder> accountHolders);

    AccountHolder getAccountHolder(Long id);

    Collection<AccountHolder> getAllAccountHolders();
}
