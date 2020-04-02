/*
 * Copyright (C), 2015-2018
 * FileName: ClassPrinterBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/3/30 17:21
 * Description: Class Printer Bootstrap
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/30 17:21   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.bean.UserPwdDto;
import com.dantefung.thinkinginspringbootsamples.sample.asm.ClassMetadataReadingVisitor;
import com.dantefung.thinkinginspringbootsamples.sample.asm.ClassPrinter;
import com.dantefung.thinkinginspringbootsamples.sample.asm.SpringClassPrinter;
import org.springframework.asm.ClassReader;

/**
 * Class 文件中包含类的所有信息，如接口，字段属性，方法，在内部这些信息按照一定规则紧凑排序。
 * ASM 框会以文件流的形式读取 class 文件，然后解析过程中使用观察者模式（Visitor），
 * 当解析器碰到相应的信息委托给观察者（Visitor）。
 * @Title: ClassPrinterBootstrap
 * @Description: Class Printer Bootstrap
 * @author DANTE FUNG
 * @date 2020/3/30 17:21
 */
public class ClassPrinterBootstrap {

	public static void main(String[] args) throws Exception {
		System.out.println("org.springframework.asm.*: ---------------START---------------");
		System.out.println("\r\n");

		System.out.println("----------SpringClassPrinter Test------------");
		SpringClassPrinter classPrinter = new SpringClassPrinter();
		/*ClassReader classReader = new ClassReader(UserPwdDto.class.getName());*/
		ClassReader classReader = new ClassReader(TransactionalServiceBeanBootstrap.class.getName());
		classReader.accept(classPrinter,0);

		System.out.println("\r\n");
		System.out.println("----------ClassMetadataReadingVisitor Test------------");
		ClassMetadataReadingVisitor classMetadataReadingVisitor = new ClassMetadataReadingVisitor();
		ClassReader clr = new ClassReader(TransactionalServiceBeanBootstrap.class.getName());
		clr.accept(classMetadataReadingVisitor,0);

		System.out.println("org.springframework.asm.*: ---------------END---------------");
		System.out.println("\r\n\r\n");

		System.out.println("org.objectweb.asm.*:----------START-----------");
		ClassPrinter clsPrinter = new ClassPrinter();
		/*ClassReader classReader = new ClassReader(UserPwdDto.class.getName());*/
		org.objectweb.asm.ClassReader clsReader = new org.objectweb.asm.ClassReader(UserPwdDto.class.getName());
		clsReader.accept(clsPrinter,0);
		System.out.println("org.objectweb.asm.*:------------END------------");



	}
}
