package com.simple.payments.domain.accountholder.port.out;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccountHolderRepository {

    Optional<AccountHolder> getById(Long id);

    AccountHolder saveAccountHolder(AccountHolder accountHolder);

    List<AccountHolder> getAllAccountHolders();

    void saveAllAccountHolders(Collection<AccountHolder> accountHolders);
}
