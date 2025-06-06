package com.simple.payments.domain.transaction.model;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Transaction {

    private Long id;
    private BigDecimal amount;
    private AccountHolder sender;
    private AccountHolder receiver;
    private LocalDateTime timestamp;
}
