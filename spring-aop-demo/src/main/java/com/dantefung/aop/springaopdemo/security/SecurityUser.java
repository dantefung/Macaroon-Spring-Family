package com.dantefung.aop.springaopdemo.security;

import java.util.HashSet;
import java.util.Set;

/**
 * 模拟权限用户
 *
 * @author L.cm
 */
public class SecurityUser {
	private Long userId;
	private String name;
	private Set<String> roles = new HashSet<>();
	private Set<String> permissions = new HashSet<>();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addRole(String role) {
		this.roles.add(role);
	}

	public void addPermission(String permission) {
		this.permissions.add(permission);
	}

	public Set<String> getRoles() {
		return roles;
	}

	public Set<String> getPermissions() {
		return permissions;
	}
}
