package com.dantefung.tx.sample.user.service;

import com.dantefung.tx.sample.user.entity.User;

import java.util.List;

public interface UserService {

	User getUserById(Integer id);

	List<User> getUserList();

	int add(User user);

	int update(Integer id, User user);

	int delete(Integer id);

	void batchAdd(User first, User second);

	void batchAddUseMyTransaction(User first, User second);

	void addByMapper(User user);
}