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
public class HotdealsEvent extends BaseObject {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonProperty("event_id")
	private String eventId;

	@Getter
	@Setter
	@JsonProperty("date_from")
	@JsonSerialize(using = DateTimeSerializer.class)
	// @JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime dateFrom;

	@Getter
	@Setter
	@JsonProperty("date_to")
	@JsonSerialize(using = DateTimeSerializer.class)
	// @JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime dateTo;

	@Getter
	@Setter
	@JsonProperty("event_name")
	private String eventName;

	@Getter
	@Setter
	@JsonProperty("event_type")
	private String eventType;

	@Getter
	@Setter
	@JsonProperty("fcfs_num")
	private int fcfsNum;

	public HotdealsEvent() {
		// ignore...
	}

}
