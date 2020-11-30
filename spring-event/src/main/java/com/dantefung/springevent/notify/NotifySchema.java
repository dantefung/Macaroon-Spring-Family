package com.dantefung.springevent.notify;

public enum NotifySchema {

	LOAD_BALANCE("lb"), HTTP("http"), JVM("jvm"), MQ("mq");

	private String schema;

	private NotifySchema(String schema) {
		this.schema = schema;
	}

	public String schema() {
		return this.schema;
	}
}
