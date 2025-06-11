package com.simple.payments.integration;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.math.BigDecimal;
import java.util.Random;

public class Fixture {
    public static AccountHolder createAccountHolder(AccountHolderType accountHolderType) {
        int randomId = new Random().nextInt();
        return new AccountHolder(
            null,
            "joao-" + randomId,
            "123456" + randomId,
            "test@test" + randomId +".com",
            new BigDecimal(100),
            accountHolderType);
    }

}
