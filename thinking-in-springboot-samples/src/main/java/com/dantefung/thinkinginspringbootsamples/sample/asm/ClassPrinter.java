/*
 * Copyright (C), 2015-2018
 * FileName: ClassPrinter
 * Author:   DANTE FUNG
 * Date:     2020/3/30 17:15
 * Description: asm sample
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/30 17:15   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.sample.asm;


import org.springframework.asm.*;

import static org.springframework.asm.Opcodes.ASM5;

/**
 * @Title: ClassPrinter
 * @Description: asm sample 
 * @author DANTE FUNG
 * @date 2020/3/30 17:15
 */
public class ClassPrinter extends ClassVisitor {

	public ClassPrinter() {
		super(ASM5);
	}

	public ClassPrinter(int api, ClassVisitor classVisitor) {
		super(api, classVisitor);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		System.out.printf("%s extends %s{\r\n", name, superName);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		String className = Type.getType(descriptor).getClassName();
		System.out.printf("ClassPrinter.visitAnnotation()  %s  %s   \r\n", className, descriptor);
		return null;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
		System.out.printf("  %s  %s  %s\r\n", descriptor, name, Type.getType(descriptor).getClass());
		return null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
			String[] exceptions) {
		System.out.printf("   %s%s\r\n", name, descriptor);
		return null;
	}

	@Override
	public void visitEnd() {
		System.out.printf("}\r\n");

	}


}
