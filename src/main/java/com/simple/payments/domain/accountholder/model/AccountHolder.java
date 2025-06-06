package com.simple.payments.domain.accountholder.model;

import com.simple.payments.adapters.outbound.persistence.accountholder.entity.AccountHolderType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AccountHolder {

    private Long id;
    private String name;
    private String document;
    private String email;
    private BigDecimal balance;
    private AccountHolderType type;

}
