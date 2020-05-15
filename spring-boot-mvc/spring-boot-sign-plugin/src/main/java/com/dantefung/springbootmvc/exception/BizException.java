package com.dantefung.springbootmvc.exception;

import com.dantefung.springbootmvc.utils.JsonUtil;
import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务异常<br>
 * 用途：用于在处理业务时，向框架抛出异常，框架将该异常作为错误信息进行输出。<br>
 * 使用场景：比如在深层业务代码判断参数无正确，就可以直接抛出该异常。<br>
 * 该异常可以控制事务是否回滚，如果不明确指定是否回滚，则code不等于0时回滚，等于0是不回滚。<br>
 *
 * <pre>
 * code rule：{2}{2}{4}
 *            |  |  |
 *           sys |  |
 *            module|
 *                error
 * 如：[10020001]前四位数为系统+模块编号，后4位为错误代码
 *
 * 10xx - 账户平台
 * 11xx - 基础平台
 * 12xx - 支付结算平台
 *
 * 90xx - 调度平台
 *
 * 9995 - APP调用 - 框架级别
 * 9996 - 回调服务 - 框架级别
 * 9997 - 协作服务 - 框架级别
 * 9998 - 系统错误 - 框架级别
 *
 * 9999 - 公共异常
 * </pre>
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class BizException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 系统不舒服 */
	public static final BizException UNKONW_EXCEPTION = new BizException(99990001, "系统不舒服！");
	/** 参数校验失败 */
	public static final BizException PARAM_CHECK_EXCEPTION = new BizException(99990002, "参数校验失败");
	/** 包含非法字符 */
	public static final BizException ERROR_CHAR_EXCEPTION = new BizException(99990003, "包含非法字符");

	/** 内部服务调用异常 */
	public static final BizException FEIGN_CAUSE_EXCEPTION = new BizException(99990004, "系统不舒服！");

	/** 系统不舒服 */
	public static final BizException OBJECT_CONVER_EXCEPTION = new BizException(99990005, "数据异常！");
	
	/**
	 * 接口超时
	 */
	public static final BizException TIMEOUT_EXCEPTION = new BizException(99990006, "系统超时，请稍后重试");
	/**
	 * api接口不存在 404
	 */
	public static final BizException NOT_FOUND_EXCEPTION = new BizException(99990007, "接口不存在404");
	
	private final String msg;
	private final int code;
	private final Map<String,Object> objectMap = new HashMap<>();

	/**
	 * 构造函数
	 * @param code
	 * @param msg
	 */
	protected BizException(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	private BizException(int code, String msg, Map<String,Object> objectMap) {
		super(msg);
		this.msg = msg;
		this.code = code;
		this.objectMap.putAll(objectMap);
	}

	/**
	 * 格式化异常信息
	 * @param args
	 * @return
	 */
	public BizException fmt(Object... args) {
		//格式化文本
		String message = this.msg;
		if (args != null && args.length > 0) {
			 message = MessageFormat.format(message, args);
		}
		return new BizException(this.code, message,this.objectMap);
	}

	/**
	 * 异常数据信息
	 * @param key
	 * @param obj
	 */
	public BizException addObj(String key,Object obj) {
		this.objectMap.put(key,obj);
		return this;
	}


	/**
	 * 注意：请勿修改
	 * @return
	 */
	@Override
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("className", BizException.class.getName());
		map.put("code", code);
		map.put("msg", msg);
		return JsonUtil.map2Json(map);
	}

}
