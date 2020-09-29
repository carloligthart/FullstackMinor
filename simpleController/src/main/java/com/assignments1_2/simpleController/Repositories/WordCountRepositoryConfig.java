package com.assignments1_2.simpleController.Repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class WordCountRepositoryConfig {

    @Bean
    @Qualifier("wordCountMemory")
    public HashMap<String, Integer> wordCountMemory() {
        return new HashMap<>();
    }
}
