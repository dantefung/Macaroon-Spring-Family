package com.dantefung.aop.springaopdemo.core.result;

import com.dantefung.aop.springaopdemo.exception.ServiceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 响应信息主体
 *
 * @param <T> 泛型标记
 * @author L.cm
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class R<T> implements Serializable {
	private static final long serialVersionUID = -1160662278280275915L;

	private int code;
	private boolean success;
	private String msg;
	private T data;

	private R(IResultCode resultCode) {
		this(resultCode, resultCode.getMsg(), null);
	}

	private R(IResultCode resultCode, String msg) {
		this(resultCode, msg, null);
	}

	private R(IResultCode resultCode, T data) {
		this(resultCode, resultCode.getMsg(), data);
	}

	private R(IResultCode resultCode, String msg, T data) {
		this.code = resultCode.getCode();
		this.msg = msg;
		this.data = data;
		this.success = SystemCode.SUCCESS == resultCode;
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isSuccess(@Nullable R<?> result) {
		return Optional.ofNullable(result)
			.map(r -> r.code)
			.map(code -> SystemCode.SUCCESS.code == code)
			.orElse(Boolean.FALSE);
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isNotSuccess(@Nullable R<?> result) {
		return !R.isSuccess(result);
	}

	/**
	 * 获取data
	 *
	 * @param result Result
	 * @param <T>    泛型标记
	 * @return 泛型对象
	 */
	@Nullable
	public static <T> T getData(@Nullable R<T> result) {
		return Optional.ofNullable(result)
			.filter(r -> r.success)
			.map(x -> x.data)
			.orElse(null);
	}

	/**
	 * 返回成功
	 *
	 * @param <T> 泛型标记
	 * @return Result
	 */
	public static <T> R<T> success() {
		return new R<>(SystemCode.SUCCESS);
	}

	/**
	 * 成功-携带数据
	 *
	 * @param data 数据
	 * @param <T>  泛型标记
	 * @return Result
	 */
	public static <T> R<T> success(@Nullable T data) {
		return new R<>(SystemCode.SUCCESS, data);
	}

	/**
	 * 根据状态返回成功或者失败
	 *
	 * @param status 状态
	 * @param msg    异常msg
	 * @param <T>    泛型标记
	 * @return Result
	 */
	public static <T> R<T> status(boolean status, String msg) {
		return status ? R.success() : R.fail(msg);
	}

	/**
	 * 根据状态返回成功或者失败
	 *
	 * @param status 状态
	 * @param sCode  异常code码
	 * @param <T>    泛型标记
	 * @return Result
	 */
	public static <T> R<T> status(boolean status, IResultCode sCode) {
		return status ? R.success() : R.fail(sCode);
	}

	/**
	 * 返回失败信息，用于 web
	 *
	 * @param msg 失败信息
	 * @param <T> 泛型标记
	 * @return {Result}
	 */
	public static <T> R<T> fail(String msg) {
		return new R<>(SystemCode.FAILURE, msg);
	}

	/**
	 * 返回失败信息
	 *
	 * @param rCode 异常枚举
	 * @param <T>   泛型标记
	 * @return {Result}
	 */
	public static <T> R<T> fail(IResultCode rCode) {
		return new R<>(rCode);
	}

	/**
	 * 返回失败信息
	 *
	 * @param rCode 异常枚举
	 * @param msg   失败信息
	 * @param <T>   泛型标记
	 * @return {Result}
	 */
	public static <T> R<T> fail(IResultCode rCode, String msg) {
		return new R<>(rCode, msg);
	}

	/**
	 * 当 result 不成功时：直接抛出失败异常，返回传入的 result。
	 *
	 * @param result R
	 */
	public static void throwOnFail(R<?> result) {
		if (R.isNotSuccess(result)) {
			throw new ServiceException(result);
		}
	}

	/**
	 * 当 result 不成功时：直接抛出失败异常，返回传入的 rCode
	 *
	 * @param result R
	 * @param rCode 异常枚举
	 */
	public static void throwOnFail(R<?> result, IResultCode rCode) {
		if (R.isNotSuccess(result)) {
			throw new ServiceException(rCode);
		}
	}

	/**
	 * 当 result 不成功时：直接抛出失败异常，返回传入的 rCode、message
	 *
	 * @param result R
	 * @param rCode 异常枚举
	 * @param msg 失败信息
	 */
	public static void throwOnFail(R<?> result, IResultCode rCode, String msg) {
		if (R.isNotSuccess(result)) {
			throw new ServiceException(rCode, msg);
		}
	}

	/**
	 * 当 status 不为 true 时：直接抛出失败异常 rCode
	 *
	 * @param status status
	 * @param rCode 异常枚举
	 */
	public static void throwOnFalse(boolean status, IResultCode rCode) {
		if (!status) {
			throw new ServiceException(rCode);
		}
	}

	/**
	 * 当 status 不为 true 时：直接抛出失败异常 rCode、message
	 *
	 * @param status status
	 * @param rCode 异常枚举
	 * @param msg 失败信息
	 */
	public static void throwOnFalse(boolean status, IResultCode rCode, String msg) {
		if (!status) {
			throw new ServiceException(rCode, msg);
		}
	}

	/**
	 * 直接抛出失败异常，抛出 code 码
	 *
	 * @param rCode IResultCode
	 */
	public static void throwFail(IResultCode rCode) {
		throw new ServiceException(rCode);
	}

	/**
	 * 直接抛出失败异常，抛出 code 码
	 *
	 * @param rCode   IResultCode
	 * @param message 自定义消息
	 */
	public static void throwFail(IResultCode rCode, String message) {
		throw new ServiceException(rCode, message);
	}
}