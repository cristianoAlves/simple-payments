package com.simple.payments.Fixtures;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.math.BigDecimal;
import java.util.Random;

public class AccountHolderFixture {
    public static AccountHolder createAccountHolder(AccountHolderType accountHolderType) {
        return createAccountHolder(null, accountHolderType);
    }

    public static AccountHolder createAccountHolder(Long id, AccountHolderType accountHolderType) {
        return createAccountHolder(id, new BigDecimal(100L), accountHolderType);
    }

    public static AccountHolder createAccountHolder(Long id, BigDecimal balance, AccountHolderType accountHolderType) {
        int randomId = new Random().nextInt();
        return new AccountHolder(
            id,
            "joao-" + randomId,
            "123456" + randomId,
            "test@test" + randomId +".com",
            balance,
            accountHolderType);
    }

}
