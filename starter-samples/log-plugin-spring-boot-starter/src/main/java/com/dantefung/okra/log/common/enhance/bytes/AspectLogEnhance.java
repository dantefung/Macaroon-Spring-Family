package com.dantefung.okra.log.common.enhance.bytes;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author Bryan.Zhang
 * 利用javassit进行对LOG框架进行切面增强
 */
public class AspectLogEnhance {

    public static void enhance(){
        try{
            //logback的增强(包括同步和异步日志)
            CtClass cc = null;
            try{
                ClassPool pool = ClassPool.getDefault();
                pool.importPackage("com.dantefung.okra.log.common.enhance.bytes.logback.LogbackBytesEnhance");

                cc = pool.get("ch.qos.logback.classic.Logger");

                if(cc != null){
                    CtMethod ctMethod = cc.getDeclaredMethod("buildLoggingEventAndAppend");
                    ctMethod.setBody("{return LogbackBytesEnhance.enhance($1,$2,$3,$4,$5,$6,this);}");
                    cc.toClass();
                    System.out.println("logback同步日志增强成功");
                }
            }catch (Exception e){
            	e.printStackTrace();
                System.out.println("logback同步日志增强失败");
            }

            //log4j日志增强(包括同步和异步日志)
            /*try{
                ClassPool pool = ClassPool.getDefault();
                pool.importPackage("com.yomahub.tlog.core.enhance.bytes.log4j.Log4jBytesEnhance");

                cc = pool.get("org.apache.log4j.AppenderSkeleton");

                if(cc != null){
                    CtMethod ctMethod = cc.getDeclaredMethod("doAppend");
                    ctMethod.setBody("{Log4jBytesEnhance.enhance($1,closed,name,this);}");
                    cc.toClass();
                    System.out.println("log4j日志增强成功");
                }
            }catch (Exception e){
                System.out.println("log4j日志增强失败");
            }*/
        }catch (Throwable t){
            t.printStackTrace();
        }
    }
}
