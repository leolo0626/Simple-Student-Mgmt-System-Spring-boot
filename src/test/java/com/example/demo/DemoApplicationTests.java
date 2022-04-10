package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:application-it.properties"
)
class DemoApplicationTests {

	@Test
	void contextLoads() {
//		Assertions.fail("Oops test failed --- 1");
	}

}
