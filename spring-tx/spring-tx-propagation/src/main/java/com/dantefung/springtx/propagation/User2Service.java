package com.dantefung.springtx.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class User2Service {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void required(String name) {
        this.jdbcTemplate.update("insert into user2(name) VALUES (?)", name);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void required_exception(String name) {
        this.jdbcTemplate.update("insert into user2(name) VALUES (?)", name);
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requires_new(String name) {
        this.jdbcTemplate.update("insert into user2(name) VALUES (?)", name);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requires_new_exception(String name) {
        this.jdbcTemplate.update("insert into user2(name) VALUES (?)", name);
        throw new RuntimeException();
    }

	/**
	 *  PROPAGATION_NESTED 开始一个 "嵌套的" 事务,  它是已经存在事务的一个真正
	 * 的子事务. 潜套事务开始执行时,  它将取得一个 savepoint.
	 * 如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分,
	 *  只有外部事务结束后它才会被提交.
	 * 由此可见, PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于,
	 * PROPAGATION_REQUIRES_NEW 完全是一个新的事务, 而 PROPAGATION_NESTED
	 * 则是外部事务的子事务, 如果外部事务 commit, 潜套事务也会被 commit,
	 *  这个规则同样适用于 roll back.
	 * @param name
	 */
	@Transactional(propagation = Propagation.NESTED)
    public void nested(String name) {
        this.jdbcTemplate.update("insert into user2(name) VALUES (?)", name);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested_exception(String name) {
        this.jdbcTemplate.update("insert into user2(name) VALUES (?)", name);
        throw new RuntimeException();
    }
}
