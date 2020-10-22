package com.dantefung.core.exception;


import com.dantefung.core.utils.JsonUtil;

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
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 注意：请勿修改
	 *
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
