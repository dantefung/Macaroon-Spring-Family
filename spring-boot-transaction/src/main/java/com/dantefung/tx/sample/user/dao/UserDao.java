package com.dantefung.tx.sample.user.dao;

import com.dantefung.tx.sample.user.entity.User;

import java.util.List;

public interface UserDao {

	User getUserById(Integer id);

	List<User> getUserList();

	int add(User user);

	int update(Integer id, User user);

	int delete(Integer id);

	void addUseMyTemplate(User user);
}