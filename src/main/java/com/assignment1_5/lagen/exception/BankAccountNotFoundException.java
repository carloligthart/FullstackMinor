package com.assignment1_5.lagen.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException() {
        super("BankAccount not found");
    }
}
