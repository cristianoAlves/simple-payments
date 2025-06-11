package com.simple.payments.adapters.inbound.rest;

import java.math.BigDecimal;

public record TransactionDTO(
    BigDecimal value,
    Long payer,
    Long payee
) {

}
