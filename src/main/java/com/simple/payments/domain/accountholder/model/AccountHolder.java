package com.simple.payments.domain.accountholder.model;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import java.math.BigDecimal;

public record AccountHolder(
    Long id,
    String name,
    String document,
    String email,
    BigDecimal balance,
    AccountHolderType type
) {

    public AccountHolder debit(final BigDecimal amount) {
        return new AccountHolder(id, name, document, email, balance.subtract(amount), type);
    }

    public AccountHolder credit(final BigDecimal amount) {
        return new AccountHolder(id, name, document, email, balance.add(amount), type);
    }
}
