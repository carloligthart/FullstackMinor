package com.assignments1_2.simpleController.Repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLOutput;
import java.util.HashMap;

@Repository
public class WordCountRepository {

    private final HashMap<String, Integer> wordCountMemory;

    public WordCountRepository(@Qualifier("wordCountMemory") HashMap<String, Integer> wordCountMemory) {
        this.wordCountMemory = wordCountMemory;
    }

    public Integer getCount(String key) {
        return wordCountMemory.get(key);
    }

    public void addCount(String key, int val) {
        wordCountMemory.put(key, val);
    }
}
