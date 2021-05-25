package com.dantefung.tx.sample.user.service;

import com.dantefung.tx.Tester;
import com.dantefung.tx.sample.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Title: UserServiceTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/01 22/51
 * @since JDK1.8
 */
public class UserServiceTest extends Tester {

	@Autowired
	private UserService userService;

	@Test
	void batchAdd() {
		Random random = new Random(100);
		int i = random.nextInt();
		User user = new User();
		user.setAge(i);
		user.setCtm(new Date());
		user.setUsername("jack"+i);

		int j = random.nextInt();
		User user2 = new User();
		user2.setUsername("rose"+j);
		user2.setAge(j);
		user2.setCtm(new Date());

		userService.batchAdd(user, user2);
	}

	@Test
	void batchAddUseMyTransaction() {
		Random random = new Random(100);
		int i = random.nextInt();
		User user = new User();
		user.setAge(i);
		user.setCtm(new Date());
		user.setUsername("jack"+i);

		int j = random.nextInt();
		User user2 = new User();
		user2.setUsername("rose"+j);
		user2.setAge(j);
		user2.setCtm(new Date());

		userService.batchAddUseMyTransaction(user, user2);
	}

	@Test
	public void addByMapper() {
		Random random = new Random(100);
		int i = random.nextInt();
		User user = new User();
		user.setAge(i);
		user.setCtm(new Date());
		user.setUsername("nike"+i);
		userService.addByMapper(user);
	}

	@Test
	public void testGetModuleById() {
		userService.getModuleById(2);
	}

}