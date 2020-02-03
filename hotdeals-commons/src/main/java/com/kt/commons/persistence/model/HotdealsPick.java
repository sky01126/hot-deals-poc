package com.kt.commons.persistence.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.kthcorp.commons.lang.BaseObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class HotdealsPick extends BaseObject {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonProperty("phone_no")
	private String phoneNo;

	@Getter
	@Setter
	@JsonProperty("event_id")
	private String eventId;

	@Getter
	@Setter
	@JsonProperty("name")
	private String name;

	@Getter
	@Setter
	@JsonProperty("agreement")
	private boolean agreement;

	@Getter
	@Setter
	@JsonProperty("pick_yn")
	private boolean pickYn;

	@Getter
	@Setter
	@JsonProperty("timestamp")
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime timestamp;

	public HotdealsPick() {
		// ignore...
	}
}
