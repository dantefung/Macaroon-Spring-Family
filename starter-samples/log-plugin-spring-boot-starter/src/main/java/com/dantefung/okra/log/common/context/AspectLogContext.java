package com.dantefung.okra.log.common.context;
/**
 * @author Bryan.Zhang
 * 日志切面的上下文，用于管理当前线程以及子线程的的增强内容
 */
public class AspectLogContext {

	/**
	 * spring-boot-devtools
	 * 深层原理是使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为restart ClassLoader,
	 * 这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间。
	 * 会导致InheritableThreadLocal取不到值
	 */
    private static InheritableThreadLocal<String> logValueTL = new InheritableThreadLocal<String>(){
		@Override
		protected String initialValue() {
			//System.out.println("ThreadLocal is initialized");
			return "";
		}

		@Override
		protected String childValue(String parentValue) {
			//System.out.println("ThreadLocal childValue is initialized, parent: "+parentValue);
			String value = initialValue();
			value = parentValue;
			return value;
		}
	};

    public static void putLogValue(String logValue){
        logValueTL.set(logValue);
    }

    public static String getLogValue(){
        return logValueTL.get();
    }

    public static void remove(){
        logValueTL.remove();
    }
}
