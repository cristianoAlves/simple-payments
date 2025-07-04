package com.simple.payments.adapters.inbound.rest;

import com.simple.payments.domain.accountholder.model.AccountHolder;
import com.simple.payments.domain.accountholder.port.in.AccountHolderUseCase;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<AccountHolder> addAccountHolder(@RequestBody AccountHolder accountHolder) {
        AccountHolder accountHolderResponse = accountHolderUseCase.saveAccountHolder(accountHolder);
        return new ResponseEntity<>(accountHolderResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<AccountHolder>> listAllAccountHolder() {
        return new ResponseEntity<>(accountHolderUseCase.getAllAccountHolders(), HttpStatus.OK);
    }
}
