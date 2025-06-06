package com.simple.payments.adapters.outbound.persistence.accountholder.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccountHolderType {
    REGULAR(true),
    MERCHANT(false);

    private final boolean ableToSendTransactions;
}
