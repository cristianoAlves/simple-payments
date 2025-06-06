package com.simple.payments.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErrorDto {
    private String errorMessage;
    private int errorCode;
}
