package com.simple.payments.adapters.inbound.rest;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountHolderController {

    private final AccountHolderUseCase accountHolderUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Void addAccountHolder(@RequestBody AccountHolder accountHolder) {
        accountHolderUseCase.saveAccountHolder(accountHolder);
        return null;
    }
}
