package com.simple.payments.adapters.inbound.rest;

public record ErrorDto(
    String errorMessage,
    int errorCode
) {}
