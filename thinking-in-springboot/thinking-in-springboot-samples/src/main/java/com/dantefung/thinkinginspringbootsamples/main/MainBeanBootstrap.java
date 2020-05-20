package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.annotation.MainBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@MainBean(value = "mainbean")
//@MainBean(beanName = "mainbean")
public class MainBeanBootstrap {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainBeanBootstrap.class);

		String[] beannames = context.getBeanNamesForType(MainBeanBootstrap.class);

		System.out.println(beannames[0]);

		context.close();
	}
}
