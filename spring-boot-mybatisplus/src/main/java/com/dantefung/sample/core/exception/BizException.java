package com.dantefung.sample.core.exception;

import com.dantefung.sample.core.utils.JsonUtil;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class BizException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final BizException UNKONW_EXCEPTION = new BizException(99990001, "系统不舒服！");
    public static final BizException PARAM_CHECK_EXCEPTION = new BizException(99990002, "参数校验失败");
    public static final BizException ERROR_CHAR_EXCEPTION = new BizException(99990003, "包含非法字符");
    public static final BizException FEIGN_CAUSE_EXCEPTION = new BizException(99990004, "系统不舒服！");
    public static final BizException OBJECT_CONVER_EXCEPTION = new BizException(99990005, "数据异常！");
    public static final BizException TIMEOUT_EXCEPTION = new BizException(99990006, "系统超时，请稍后重试");
    public static final BizException NOT_FOUND_EXCEPTION = new BizException(99990007, "接口不存在404");
    private final String msg;
    private final int code;

    protected BizException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BizException fmt(Object... args) {
        String message = this.msg;
        if (args != null && args.length > 0) {
            message = MessageFormat.format(message, args);
        }

        return new BizException(this.code, message);
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

    public String toString() {
        Map<String, Object> map = new HashMap();
        map.put("className", BizException.class.getName());
        map.put("code", this.code);
        map.put("msg", this.msg);
        return JsonUtil.map2Json(map);
    }
}