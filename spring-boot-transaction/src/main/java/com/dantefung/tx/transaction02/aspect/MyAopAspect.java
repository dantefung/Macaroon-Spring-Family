package com.dantefung.tx.transaction02.aspect;

import com.dantefung.tx.transaction02.MyTransctionHolder;
import com.dantefung.tx.transaction02.MyTransctionManager;
import com.dantefung.tx.transaction02.annotation.MyTransction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
@Aspect
public class MyAopAspect {

	//注入获取连接
	@Autowired
	private MyTransctionHolder myTransctionHolder;

	//注入事务管理
	@Autowired
	private MyTransctionManager myTransctionManager;


	/**
	 *@Description 说明：
	 * 1、 使用@Around环绕执行  执行前和执行后
	 * 2、 采用@annotation 使用了MuyanTransction注解的类执行此方法
	 * 3、 获取连接，这样保证被MuyanTransction注解的方法采用了同一个连接connection
	 * 4、 同一个connection共有一个事务，如果失败，则全部回滚
	 *@Author DANTE FUNG
	 *@Date 2021-03-01 23:12:09
	 *@Param [proceedingJoinPoint]
	 *@return void
	 */
	@Around(value = "@annotation(myTransction)")
	public Object txAroundOption(ProceedingJoinPoint proceedingJoinPoint, MyTransction myTransction) {
		//开始获取连接
		Connection connection = myTransctionHolder.getConnection();

		Object proceed = null;

		try {
			//开始执行前开启事务管理
			myTransctionManager.startTx(connection);
			//开始执行操作
			proceed = proceedingJoinPoint.proceed();

			//执行之后提交事务
			myTransctionManager.commitTx(connection);

		} catch (Exception e) {
			e.printStackTrace();
			//回滚事务
			myTransctionManager.rollBackTx(connection);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			//回滚事务
			myTransctionManager.rollBackTx(connection);
		} finally {
			//关闭事务
			myTransctionManager.closeTx(connection);
		}

		return proceed;

	}


}