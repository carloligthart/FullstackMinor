package com.assignments1_2.simpleController.Controllers;

import com.assignments1_2.simpleController.Services.IStringService;
import com.assignments1_2.simpleController.Services.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringController {

    @Autowired
    private WordCountService wordCountService;

    @Autowired
    private IStringService stringService;


    @RequestMapping("/hello")
    public String SayHello() {
        return "hello";
    }

    @RequestMapping("/hi")
    public String SayHi() {
        return "hi";
    }

    @RequestMapping("/stringreverser/{text}")
    public String reverseString(@PathVariable String text) {
        return stringService.reverseString(text);
    }

    @RequestMapping("/countwords/{text}")
    public int countWords(@PathVariable String text) {
        return wordCountService.getWordCount(text);
    }

}
