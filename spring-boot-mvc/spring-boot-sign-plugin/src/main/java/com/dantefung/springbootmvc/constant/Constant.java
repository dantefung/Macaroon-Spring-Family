/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.dantefung.springbootmvc.constant;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";
    /**
     *  redis图片验证码头
     */
    public static final String IMG_CAPTCHA_KEY_HEADER = "imgcaptcha";
    /**
     *  验证码过期时间（秒）
     */
    public static final Long IMG_CAPTCHA_FAILURE_TIME = 300L;
	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 通用请求参数:调用能力中心后台服务接口时，如果接口要求验证用户身份token，则使用该Header Key在Http header中传入
     */
    public static final String TOKEN_HTTP_HEADER_KEY ="X-OS-BOOT-TOKEN";


    /**
     * 通用请求参数:调用能力中心后台服务接口时，需要提供调用的应用系统ID，使用该Header Key在Http header中传入
     */
    public static final String APPSYSID_HTTP_HEADER_KEY = "X-OS-BOOT-APPSYSID";


    /**
     * Shiro 要求的认证Header Key
     */
    public static final String SHIRO_AUTHZ_HEADER_KEY = "Authorization";

    /**
     * 由于身份认证使用CAS，不使用Cas，因此写入固定的值，跳过Shiro的认证模块
     */
    public static final String SHIRO_AUTHZ_HEADER_VALUE = "Basic PASSTHR";
    
    /**
     * 接口调用时的时间戳
     */
    public static final String TIMESTAMP_HTTP_HEADER_KEY="X-OS-BOOT-TIMESTAMP";
    /**
     * 接口签名值
     */
    public static final String SIGN_HTTP_HEADER_KEY="X-OS-BOOT-SIGN";

    /**
     * SAAS化的租户编码
     */
    public static final String TENANT_CODE_HEADER_KEY = "X-OS-BOOT-TENANT-CODE";
}

