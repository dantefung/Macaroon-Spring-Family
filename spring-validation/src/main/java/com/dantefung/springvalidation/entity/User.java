package com.dantefung.springvalidation.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class User {
	@NotNull(message = "username can not be null")
	@Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "username is illegal")
	private String username;
	@Size(min = 5, max = 10, message = "password's length is illegal", groups = Add.class)
	private String password;
}