package com.simple.payments.domain.transaction.model;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
    Long id,
    BigDecimal amount,
    AccountHolder sender,
    AccountHolder receiver,
    LocalDateTime timestamp
) {

}
