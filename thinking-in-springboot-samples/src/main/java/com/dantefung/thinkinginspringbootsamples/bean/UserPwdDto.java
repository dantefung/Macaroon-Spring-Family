package com.dantefung.thinkinginspringbootsamples.bean;

import com.dantefung.thinkinginspringbootsamples.validate.group.Add;
import com.dantefung.thinkinginspringbootsamples.validate.group.Reset;
import com.dantefung.thinkinginspringbootsamples.validate.group.Update;
import com.dantefung.thinkinginspringbootsamples.validate.group.Verify;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户密码DTO
 * 包含用户id、旧密码、新密码信息
 * @Author:xuanjian
 * @Data:   2019-07-26
 */
@Data
public class UserPwdDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**UUID(36位)*/
    @NotBlank(message = "用户id(userId)不能为空",groups = {Update.class, Verify.class, Reset.class})
    private String userId;
    /**手机号*/
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$",message = "请输入正确的手机号",groups = Add.class )
    @NotBlank(message = "手机号(mobile)不能为空",groups = Add.class)
    private String mobile;
    /**旧密码*/
    @NotBlank(message = "旧密码(oldPwd)不能为空",groups = {Update.class,Verify.class})
    private String oldPwd;
    /**新密码*/
    @Pattern(regexp = "^(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,18}$",message = "密码格式不正确，支持8～18位数字、大小写字母的组合",groups = {Update.class} )
    @NotBlank(message = "新密码(newPwd)不能为空",groups = {Update.class})
    private String newPwd;
    /**密码*/
    @Pattern(regexp = "^(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,18}$",message = "密码格式不正确，支持8～18位数字、大小写字母的组合",groups = Add.class)
    @NotBlank(message = "密码不能为空",groups = Add.class)
    private String userPwd;
    /**验证码*/
    @NotBlank(message = "验证码不能为空",groups = Add.class)
    private String checkCode;
}
