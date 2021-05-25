package com.dantefung.springtx.propagation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Demo6Test {

    private TxService txService;
    private JdbcTemplate jdbcTemplate;

    //每个@Test用例执行之前先启动一下spring容器，并清理一下user1、user2中的数据
    @Before
    public void before() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        txService = context.getBean(TxService.class);
        jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.update("truncate table user1");
        jdbcTemplate.update("truncate table user2");
    }

    @After
    public void after() {
        System.out.println("user1表数据：" + jdbcTemplate.queryForList("SELECT * from user1"));
        System.out.println("user2表数据：" + jdbcTemplate.queryForList("SELECT * from user2"));
    }

	/**
	 * user1、user2表都写入数据
	 */
	@Test
    public void notransaction_exception_required_required() {
        txService.notransaction_exception_required_required();
    }

	/**
	 * user1表写入数据, user2表无数据(回滚掉了)
	 */
	@Test
    public void notransaction_required_required_exception() {
        txService.notransaction_required_required_exception();
    }

	/**
	 * user1表、user2表的数据都回滚
	 */
	@Test
    public void transaction_exception_required_required() {
        txService.transaction_exception_required_required();
    }

	/**
	 * user1表、user2表的数据都回滚
	 */
    @Test
    public void transaction_required_required_exception() {
        txService.transaction_required_required_exception();
    }

	/**
	 * user1表、user2表的数据都回滚。
	 *
	 * org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
	 */
    @Test
    public void transaction_required_required_exception_try() {
        txService.transaction_required_required_exception_try();
    }

	/**
	 * user1、user2有数据
	 */
	@Test
    public void notransaction_exception_requiresNew_requiresNew() {
        txService.notransaction_exception_requiresNew_requiresNew();
    }

	/**
	 * user1有数据，user2无数据
	 *
	 */
	@Test
    public void notransaction_requiresNew_requiresNew_exception() {
        txService.notransaction_requiresNew_requiresNew_exception();
    }

	/**
	 * user1表数据：[]
	 * user2表数据：[{id=1, name=李四}, {id=2, name=王五}]
	 *
	 * 外围方法开启事务，插入“张三”方法和外围方法一个事务
	 * ，插入“李四”方法、插入“王五”方法分别在独立的新建事务中，
	 * 外围方法抛出异常只回滚和外围方法同一事务的方法，故插入“张三”的方法回滚。
	 */
	@Test
    public void transaction_exception_required_requiresNew_requiresNew() {
        txService.transaction_exception_required_requiresNew_requiresNew();
    }

	/**
	 * user1表数据：[]
	 * user2表数据：[{id=1, name=李四}]
	 *
	 *	外围方法开启事务，插入“张三”方法和外围方法一个事务，
	 * 	插入“李四”方法、插入“王五”方法分别在独立的新建事务中。
	 * 	插入“王五”方法抛出异常，首先插入 “王五”方法的事务被回滚，
	 * 	异常继续抛出被外围方法感知，外围方法事务亦被回滚，故插入“张三”方法也被回滚。
	 */
	@Test
    public void transaction_required_requiresNew_requiresNew_exception() {
        txService.transaction_required_requiresNew_requiresNew_exception();
    }

	/**
	 * 	外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中
	 * 	插入“王五”方法抛出异常，首先插入“王五”方法的事务被回滚，异常被catch不会被外围方法感知，外围方法事务不回滚，故插入“张三”方法插入成功。
	 */
	@Test
    public void transaction_required_requiresNew_requiresNew_exception_try() {
        txService.transaction_required_requiresNew_requiresNew_exception_try();
    }

	/**
	 * user1表数据：[{id=1, name=张三}]
	 * user2表数据：[{id=1, name=李四}]
	 *
	 * PROPAGATION_NESTED : 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。
	 */
	@Test
    public void notransaction_exception_nested_nested() {
        txService.notransaction_exception_nested_nested();
    }

	/**
	 * user1表数据：[{id=1, name=张三}]
	 * user2表数据：[]
	 */
	@Test
    public void notransaction_nested_nested_exception() {
        txService.notransaction_nested_nested_exception();
    }

	/**
	 * user1表数据：[]
	 * user2表数据：[]
	 *
	 */
	@Test
    public void transaction_exception_nested_nested() {
        txService.transaction_exception_nested_nested();
    }

    @Test
    public void transaction_nested_nested_exception() {
        txService.transaction_nested_nested_exception();
    }

    @Test
    public void transaction_nested_nested_exception_try() {
        txService.transaction_nested_nested_exception_try();
    }

}
