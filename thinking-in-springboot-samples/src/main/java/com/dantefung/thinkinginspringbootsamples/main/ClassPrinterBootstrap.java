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
import com.dantefung.thinkinginspringbootsamples.sample.asm.ClassPrinter;
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
		ClassPrinter classPrinter = new ClassPrinter();
		/*ClassReader classReader = new ClassReader(UserPwdDto.class.getName());*/
		ClassReader classReader = new ClassReader(TransactionalServiceBeanBootstrap.class.getName());
		classReader.accept(classPrinter,0);

	}
}
