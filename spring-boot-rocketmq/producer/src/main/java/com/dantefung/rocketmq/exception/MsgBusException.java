package com.dantefung.rocketmq.exception;


import java.text.MessageFormat;

public class MsgBusException extends RuntimeException {
	private final String msg;
	private final int code;
    public static final MsgBusException MQ_SEND_MSG_ERROR = new MsgBusException(99990501, "发送消息到RocketMQ后，返回失败状态 - [{0}]。");

    public MsgBusException(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
    }

	public MsgBusException fmt(Object... args) {
		String message = this.msg;
		if (args != null && args.length > 0) {
			message = MessageFormat.format(message, args);
		}

		return new MsgBusException(this.code, message);
	}
}
