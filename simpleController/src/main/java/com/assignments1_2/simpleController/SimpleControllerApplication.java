package com.assignments1_2.simpleController;

import com.assignments1_2.simpleController.Services.IStringService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class SimpleControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleControllerApplication.class, args);
	}

	@Service
	@Profile("profile1")
	public static class StringService implements IStringService {

		public String reverseString(String text) {
			return new StringBuilder(text).reverse().toString();
		}
	}
}
