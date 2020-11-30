package com.dantefung.springevent.event;

import com.dantefung.springevent.notify.NotifyUri;
import org.springframework.context.ApplicationEvent;

/**
 * 通知事件，通知下游系统
 */
public class NotifyEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6046147087723055945L;

	private NotifyUri uri;

	public NotifyEvent(Object source, NotifyUri uri) {
		super(source);
		this.uri = uri;
	}

	public NotifyUri getUri() {
		return this.uri;
	}
}
