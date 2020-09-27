package com.assignments1_2.simpleController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleControllerApplicationTests {

	@Autowired
	private SimpleControllerApplication.StringService stringService;  // maybe use the interface and use profiles?


	@Test
	void contextLoads() {
	}

}
