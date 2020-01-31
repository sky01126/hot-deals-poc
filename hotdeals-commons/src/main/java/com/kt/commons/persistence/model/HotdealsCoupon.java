package com.kt.commons.persistence.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kthcorp.commons.lang.BaseObject;

import lombok.Getter;
import lombok.Setter;

@Component
public class HotdealsCoupon extends BaseObject {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonProperty("event_id")
	private String eventId;

	@Getter
	@Setter
	@JsonProperty("close_yn")
	private boolean closed = false;

	public HotdealsCoupon() {
		// ignore..
	}

	public HotdealsCoupon(String eventId, boolean closed) {
		this.eventId = eventId;
		this.closed = closed;
	}

}
