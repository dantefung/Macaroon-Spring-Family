package com.dantefung.thinkinginspringbootsamples.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 模拟权限用户
 */
public class SecurityUser {
	private Long userId;
	private String name;
	private Set<String> roles = new HashSet<>();
	private Set<String> permissions = new HashSet<>();
	private Map<String, String> extraParams = new HashMap<>();

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

	public void putExtraParams(String key, String value) {
		this.extraParams.put(key, value);
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

	public Map<String, String> getExtraParams() {
		return this.extraParams;
	}
}
