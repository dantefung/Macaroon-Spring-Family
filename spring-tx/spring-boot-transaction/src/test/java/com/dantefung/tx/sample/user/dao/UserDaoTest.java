package com.dantefung.tx.sample.user.dao;

import com.dantefung.tx.SpringBootTransctionApplication;
import com.dantefung.tx.Tester;
import com.dantefung.tx.sample.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Title: UserDaoTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/01 22/41
 * @since JDK1.8
 */
public class UserDaoTest extends Tester {

	@Autowired
	private UserDao userDao;

	@Test
	void add() {
		Random random = new Random(100);
		int i = random.nextInt();
		User user = new User();
		user.setAge(i);
		user.setCtm(new Date());
		user.setUsername("jack"+i);

		userDao.add(user);

		int j = random.nextInt();
		User user2 = new User();
		user2.setUsername("rose"+j);
		user2.setAge(j);
		user2.setCtm(new Date());

		userDao.add(user2);

	}

	@Test
	public void testAddUseMyTemplate() {
		Random random = new Random(100);
		int i = random.nextInt();
		User user = new User();
		user.setAge(i);
		user.setCtm(new Date());
		user.setUsername("mike"+i);
		userDao.addUseMyTemplate(user);

	}
}