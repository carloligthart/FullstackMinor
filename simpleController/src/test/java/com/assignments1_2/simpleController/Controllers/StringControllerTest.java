package com.assignments1_2.simpleController.Controllers;

import com.assignments1_2.simpleController.Services.IStringService;
import com.assignments1_2.simpleController.Services.StringCapsService;
import com.assignments1_2.simpleController.Services.StringReverseService;
import com.assignments1_2.simpleController.Services.WordCountService;
import com.assignments1_2.simpleController.SimpleControllerApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class StringControllerTest {

    @Mock
    WordCountService wordCountService;

    @Mock
    IStringService stringService;

    @InjectMocks
    StringController stringController;

    @Test
    public void TestReverseString() {
       //Arrange
        when(stringService.reverseString("test")).thenReturn("tset");

        //Act
        String response = stringController.reverseString("test");

        //Assert
        assertEquals("tset", response);
    }

    @Test
    public void TestCountWords() {
        //Arrange
        when(wordCountService.getWordCount("this is four words")).thenReturn(4);

        //Act
        int response = stringController.countWords("this is four words");

        //Assert
        assertEquals(4, response);
    }
}