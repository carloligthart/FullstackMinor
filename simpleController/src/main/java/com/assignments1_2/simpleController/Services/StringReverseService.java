package com.assignments1_2.simpleController.Services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("Reverse")
public class StringReverseService implements IStringService {

    public String reverseString(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}
