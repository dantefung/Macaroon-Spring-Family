package com.dantefung.sample.teacher.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**  
* @Title: User
* @Description: ${todo}
* @author DANTE FUNG
* @date 2020/4/26 15:16
*/

@Data
@TableName(value = "user")
public class User implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登陆名
     */
    @TableField(value = "login_name")
    private String loginName;

    /**
     * 用户名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 密码加密盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private Byte sex;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private Byte age;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 用户类别
     */
    @TableField(value = "user_type")
    private Byte userType;

    /**
     * 用户状态
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 所属机构
     */
    @TableField(value = "organization_id")
    private Integer organizationId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_LOGIN_NAME = "login_name";

    public static final String COL_NAME = "name";

    public static final String COL_PASSWORD = "password";

    public static final String COL_SALT = "salt";

    public static final String COL_SEX = "sex";

    public static final String COL_AGE = "age";

    public static final String COL_PHONE = "phone";

    public static final String COL_USER_TYPE = "user_type";

    public static final String COL_STATUS = "status";

    public static final String COL_ORGANIZATION_ID = "organization_id";

    public static final String COL_CREATE_TIME = "create_time";
}