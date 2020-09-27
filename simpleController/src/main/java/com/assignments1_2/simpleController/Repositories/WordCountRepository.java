package com.assignments1_2.simpleController.Repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class WordCountRepository {

    private HashMap<String, Integer> wordCountMemory = new HashMap<String, Integer>();

    public Integer getCount(String key) {
        return wordCountMemory.get(key);
    }

    public void addCount(String key, int val) {
        wordCountMemory.put(key, val);
    }
}
