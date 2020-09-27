package com.assignments1_2.simpleController.Services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
class StringCapsServiceTest {

    //Arrange
    @InjectMocks
    private StringCapsService stringCapsService;

    @Test
    public void TestReverseString() {
        //Act
        String response = stringCapsService.reverseString("test");

        //Assert
        assertEquals("TEST", response);
    }

}