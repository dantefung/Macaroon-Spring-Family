package com.dantefung.aop.springaopdemo.core.result;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T>{

	private static final Integer STATUS_NORMAL = 0;

	private static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
	
	/**
	 * 参考 CommonConstant 状态
	 */
	private int code;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 结果描述，一般成功时为空
	 */
	private String message;

	/**
	 * restful返回结果
	 */
	private T result;

	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	public void error500(String message) {
		this.message = message;
		this.code = SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
	}

	public void success(String message) {
		this.message = message;
		this.code = STATUS_NORMAL;
		this.success = true;
	}

	public static Result ok(Object data) {
		Result result = new Result();
		result.success = true;
		result.code = STATUS_NORMAL;
		result.message = "";
		result.result = data;
		return result;
	}

	public static Result ok() {
		return ok(null);
	}
	
	public static Result ok(String msg) {
		Result result = new Result();
		result.success = true;
		result.code = STATUS_NORMAL;
		result.message = msg;
		return result;
    }

	public static Result fail(Integer code, String errMsg,Object o){
		Result result = new Result();
		result.code = code;
		result.message = errMsg;
		result.result = o;
		result.success = false;
		return result;
	}

	public static Result error(Integer code, String errMsg) {
		return fail(code, errMsg,null);
	}

	public static Result fail(Integer code, String errMsg) {
		return fail(code, errMsg,null);
	}

	public static Result error(String errMsg) {
		return fail(SC_INTERNAL_SERVER_ERROR_500, errMsg,null);
	}

}
