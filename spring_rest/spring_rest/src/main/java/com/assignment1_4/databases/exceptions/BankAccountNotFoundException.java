package com.assignment1_4.databases.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(Long id) {
        super("BankAccount with id " + id + " not found");
    }

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
