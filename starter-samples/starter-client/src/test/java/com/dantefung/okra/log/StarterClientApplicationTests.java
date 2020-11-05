package com.dantefung.okra.log;

import com.dantefung.okra.log.async.PersonManager;
import com.dantefung.okra.log.controller.TestController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = StarterClientApplication.class)
public class StarterClientApplicationTests {

	@Autowired
	private TestController testController;
	@Autowired
	private PersonManager personManager;

	@Test
	void contextLoads() {
		log.info("---------->{}", testController);
		testController.ok();
	}

	@Test
	public void testScheduled() {
		while (true) {

		}
	}

}
