package com.dantefung.springvalidation.service;

import com.dantefung.springvalidation.annotation.ValidGroupTag;
import com.dantefung.springvalidation.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userService")
public class UserServiceImpl implements UserService{

	@ValidGroupTag
	public void saveUser(User user, @ValidGroupTag("user") List<Class<?>> group) {
		System.out.println("enter ...");
	}

}