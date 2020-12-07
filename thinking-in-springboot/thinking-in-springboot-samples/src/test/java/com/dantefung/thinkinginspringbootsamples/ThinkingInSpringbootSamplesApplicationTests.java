package com.dantefung.thinkinginspringbootsamples;

import com.dantefung.thinkinginspringbootsamples.sample.multithread.ApplicationContextProvider;
import com.dantefung.thinkinginspringbootsamples.sample.multithread.MoniotrTask;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ThinkingInSpringbootSamplesApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void show()throws Exception{
		MoniotrTask m1=   ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
		MoniotrTask m2=ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
		MoniotrTask m3=ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
		System.out.println(m1+" => " + m1.getName());
		System.out.println(m2+" => " + m2.getName());
		System.out.println(m3+" => " + m3.getName());

	}

}
