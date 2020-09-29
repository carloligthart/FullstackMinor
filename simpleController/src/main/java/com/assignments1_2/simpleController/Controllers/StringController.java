package com.assignments1_2.simpleController.Controllers;

import com.assignments1_2.simpleController.Services.IStringService;
import com.assignments1_2.simpleController.Services.StringCapsService;
import com.assignments1_2.simpleController.Services.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class StringController {

    private final WordCountService wordCountService;
    private final IStringService stringService;

    @Autowired
    public StringController(WordCountService wordCountService, IStringService stringService) {
        this.wordCountService = wordCountService;
        this.stringService = stringService;
    }

    @GetMapping("/stringreverser/{text}")
    public String reverseString(@PathVariable String text) {
        return stringService.reverseString(text);
    }

    @GetMapping("/countwords/{text}")
    public int countWords(@PathVariable String text) {
        return wordCountService.getWordCount(text);
    }
}
