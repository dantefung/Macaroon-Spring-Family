package com.dantefung.tx.transaction;//编程事务（需要手动begin 手动回滚  手都提交）

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Component
@Scope("prototype") // 设置成原型解决线程安全
public class TransactionUtils {

	private TransactionStatus transactionStatus;

	// 获取事务源
	@Qualifier("transactionManager")
	@Autowired
	private PlatformTransactionManager dataSourceTransactionManager;

	// 开启事务
	public TransactionStatus begin() {
		transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
		return transactionStatus;
	}


	// 提交事务
	public void commit(TransactionStatus transaction) {
		dataSourceTransactionManager.commit(transaction);
	}


	// 回滚事务
	public void rollback() {
		System.out.println("rollback");
		dataSourceTransactionManager.rollback(transactionStatus);
	}
}