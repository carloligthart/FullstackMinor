package com.assignments1_2.simpleController.Services;

import com.assignments1_2.simpleController.Services.IStringService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("Caps")
public class StringCapsService implements IStringService {

    public String reverseString(String text) {
        return text.toUpperCase();
    }
}
