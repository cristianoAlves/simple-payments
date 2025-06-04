package com.simple.payments.domain.transaction.model;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.EntityAccountHolder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    private Long id;
    private BigDecimal amount;
    private EntityAccountHolder sender;
    private EntityAccountHolder receiver;
    private LocalDateTime timestamp;
}
