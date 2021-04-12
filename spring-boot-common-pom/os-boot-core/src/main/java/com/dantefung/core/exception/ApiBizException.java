package com.dantefung.core.exception;


/**
 * api接口异常
 *
 * @date 2019年7月30日 下午5:44:43
 * @version 1.0
 */
public class ApiBizException extends BizException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ApiBizException(int code, String msg) {
		super(code, msg);
	}


}
