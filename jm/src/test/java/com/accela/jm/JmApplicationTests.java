package com.accela.jm;

import com.accela.jm.contact.controller.PersonController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JmApplicationTests {

	@Autowired
	PersonController personController;

	@Test
	void contextLoads() {
		assertThat(personController).isNotNull();
	}

}
