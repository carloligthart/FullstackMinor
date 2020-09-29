package com.assignments1_2.simpleController.Services;

import com.assignments1_2.simpleController.Repositories.WordCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordCountService {

    private final WordCountRepository wordCountRepository;

    @Autowired
    public WordCountService (WordCountRepository wordCountRepository) {
        this.wordCountRepository = wordCountRepository;
    }

    public int getWordCount(String text) {
        if (wordCountRepository.getCount(text) == null) {
            return countWords(text);
        }
        else {
            return wordCountRepository.getCount(text);
        }
    }

    public int countWords(String text) {
        int count = 0;

        if (!(" ".equals(text.substring(0, 1))) || !(" ".equals(text.substring(text.length() - 1))))
        {
            for (int i = 0; i < text.length(); i++)
            {
                if (text.charAt(i) == ' ')
                {
                    count++;
                }
            }
            count = count + 1;
        }

        wordCountRepository.addCount(text, count);

        return count;
    }
}
