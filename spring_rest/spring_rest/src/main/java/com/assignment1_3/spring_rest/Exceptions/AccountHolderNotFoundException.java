package com.assignment1_3.spring_rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AccountHolderNotFoundException extends RuntimeException {

    public AccountHolderNotFoundException(Long id) {
        super("AccountHolder with id " + id + " not found");
    }

    public AccountHolderNotFoundException(String message) {
        super(message);
    }
}
