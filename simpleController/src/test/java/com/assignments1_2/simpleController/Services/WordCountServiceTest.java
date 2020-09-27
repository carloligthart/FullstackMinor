package com.assignments1_2.simpleController.Services;

import com.assignments1_2.simpleController.Repositories.WordCountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WordCountServiceTest {

    //Arrange
    @Mock
    private WordCountRepository wordCountRepository;

    @InjectMocks
    private WordCountService wordCountService;

    @Test
    public void TestGetWordCount() {
        //Arrange
        when(wordCountRepository.getCount("Test string1")).thenReturn(null);
        when(wordCountRepository.getCount("Test string 2 a")).thenReturn(4);

        //Act
        int response1 = wordCountService.getWordCount("Test string1");
        int response2 = wordCountService.getWordCount("Test string 2 a");

        //Assert
        assertEquals(2, response1);
        assertEquals(4, response2);
    }

    @Test void TestCountWords() {
        //Act
        int response1 = wordCountService.countWords("Test string 1");
        int response2 = wordCountService.countWords(" ");

        //Assert
        assertEquals(3, response1);
        assertEquals(0, response2);
    }
}