package com.dantefung.tx.sample.user.service.impl;

import com.dantefung.tx.sample.user.dao.UserDao;
import com.dantefung.tx.sample.user.dao.impl.UserMapper;
import com.dantefung.tx.sample.user.entity.User;
import com.dantefung.tx.sample.user.service.UserService;
import com.dantefung.tx.transaction.annotation.ExtTransaction;
import com.dantefung.tx.transaction02.annotation.MyTransction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
	private UserMapper userMapper;
    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }
    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
    @Override
    public int add(User user) {
        return userDao.add(user);
    }
    @Override
    public int update(Integer id, User user) {
        return userDao.update(id, user);
    }
    @Override
    public int delete(Integer id) {
        return userDao.delete(id);
    }

	@Override
	@ExtTransaction
	public void batchAdd(User first, User second) {
		userDao.add(first);
		int i = 1 / 0;
		System.out.println("################");
		userDao.add(second);
	}

	@Override
	@MyTransction
	public void batchAddUseMyTransaction(User first, User second) {
		userDao.addUseMyTemplate(first);
		int i = 1 / 0;
		System.out.println("################");
		userDao.addUseMyTemplate(second);
	}

	@Override
	public void addByMapper(User user) {
		userMapper.add(user);
	}
}