package com.dantefung.thinkinginspringbootsamples.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 权限判断el函数
 */
@Slf4j
public class SecurityFun {

	private SecurityUser securityUser;

	public SecurityFun(SecurityUser securityUser) {
		this.securityUser = securityUser;
	}

	private SecurityUser getUser() {
		return securityUser;
	}

	/**
	 * 总是返回true，表示允许所有的
	 * <code>
	 * @IAMPermissions("permitAll()")
	 * </code>
	 * @return true
	 */
	public boolean permitAll() {
		return true;
	}

	/**
	 * 已登录
	 * <code>
	 * @IAMPermissions("authenticated()")
	 * </code>
	 * @return 是否登录
	 */
	public boolean authenticated() {
		SecurityUser user = getUser();
		return user != null;
	}

	/**
	 * 判断请求是否有权限
	 *
	 * @param permission 权限表达式
	 * @return 是否有权限
	 */
	public boolean hasPermission(String permission) {
		log.info(" \r\n >>>>>>>>>>> \r\n -2-  enter SecurityFun.hasPermission ...\r\n<<<<<<<<<<<<<");
		SecurityUser user = getUser();
		// 使用PatternMatchUtils 支持 * 号，例如：get:user:list, permission = get:user:*
		return user.getPermissions().stream()
			.anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
	}

	/**
	 * 判断按钮是否有xxx:xxx权限
	 * @param role 角色
	 * @return {boolean}
	 */
	public boolean hasRole(String... role) {
		SecurityUser user = getUser();
		return Optional.ofNullable(user)
			.map(SecurityUser::getRoles)
			.map(x -> Stream.of(role).anyMatch(y -> x.contains(y)))
			.orElse(false);
	}

	/**
	 * 判断用户是否当前登录用户
	 * <code>
	 * @IAMPermissions("isMe(#userVO.userId)")
	 * </code>
	 * @param userId 用户id
	 * @return {boolean}
	 */
	public boolean isMe(final Long userId) {
		SecurityUser user = getUser();
		return Optional.ofNullable(user)
			.map(SecurityUser::getUserId)
			.map(x -> x.equals(userId))
			.orElse(false);
	}
}
