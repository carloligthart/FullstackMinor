package com.assignments1_2.simpleController.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class StringReverseServiceTest {

    @InjectMocks
    StringReverseService stringReverseService;

    @Test
    public void TestReverseString() {
        //Act
        String response = stringReverseService.reverseString("test");

        //Assert
        assertEquals("tset", response);
    }

}