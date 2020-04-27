package com.dantefung.aop.springaopdemo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 通用基础用户表
 * @Author: jeecg-boot
 * @Date: 2019-07-03
 * @Version: V1.0
 */
@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ISINORG_YES = "1";
	public static final String ISINORG_NO = "0";

	/**
	 * UUID(36位)
	 */
	private String id;
	/**
	 * 用户登录账号（字母数字等）
	 */
	private String loginAcc;
	/**
	 * 用户类型（1：外部个人用户 2：内部个人用户  11：外部机构用户 12：内部机构用户）
	 */
	private Integer userType;
	/**
	 * 用户姓名（中文）
	 */
	private String userName;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户头像
	 */
	private String userAvatar;
	/**
	 * 用户名密码（加密）
	 */
	private String userPwd;
	/**
	 * 密码Salt BCrypt
	 */
	private String pwdSalt;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 工号
	 */
	private String workNum;
	/**
	 * 身份证件类型（1：身份证（默认））
	 */
	private Integer identType;
	/**
	 * 身份证件号码
	 */
	private String identNum;
	/**
	 * 用户起始导入或注册的源系统id
	 */
	private String fromSysId;
	/**
	 * 性别（男：1 女：2）
	 */
	private Integer gender;
	/**
	 * 生日（yyyy-MM-dd）
	 */
	private String birthday;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 源系统的用户ID(唯一标识)
	 */
	private String fromSysUserId;
	/**
	 * 用户账号状态（1：启用 2：禁用）
	 **/
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者（登录账号）
	 */
	private String createBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新者（更新账号）
	 */
	private String updateBy;
	/**
	 * 是否删除（0：否，1：是）
	 */
	private Integer delFlag;
	/**
	 * 是否初始化密码（0：否，1：是）
	 */
	private Integer isInitialPwd;
	/**
	 * 是否应用管理员（0：否，1：是）
	 */
	private Integer appsysAdminFlag;

	/**
	 * qq号码
	 */
	private String qq;
	/**
	 * 微信号
	 */
	private String wechat;


	private Integer apps;
	private Integer page;
	private Integer pageSize;
	//初始状态
	private Boolean beginStatus;
	//OA返回字段
	private String bindingUser;
	//OA事项id
	private String affairId;

	private Integer setExpirationSeconds;

	private String address;

	/**
	 * 是否有组织
	 */
	private String isInOrg;
	/**
	 * 在组织内是否是主账号
	 */
	private String isMaster;

}