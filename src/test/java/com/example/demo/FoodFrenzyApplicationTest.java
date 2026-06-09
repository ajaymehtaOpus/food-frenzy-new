package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

class FoodFrenzyApplicationTest {

	@Test
	void mainShouldDelegateToSpringApplicationRun() {
		try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
			mockedSpringApplication.when(() -> SpringApplication.run(FoodFrenzyApplication.class, new String[] { "arg1" }))
					.thenReturn(null);

			assertDoesNotThrow(() -> FoodFrenzyApplication.main(new String[] { "arg1" }));

			mockedSpringApplication.verify(() -> SpringApplication.run(FoodFrenzyApplication.class, new String[] { "arg1" }),
					times(1));
		}
		assertNotNull(FoodFrenzyApplication.class);
	}

	@Test
	void applicationClassShouldBeLoadable() {
		assertNotNull(FoodFrenzyApplication.class);
	}
}