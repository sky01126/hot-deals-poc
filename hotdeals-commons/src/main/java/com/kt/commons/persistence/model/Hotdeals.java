package com.kt.commons.persistence.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
@JsonPropertyOrder(value = { // 라인 정렬
		"event_id" // 이벤트 아이디
		, "phone_num" // 핸드폰번호
		, "name" // 이름
		, "aggrement" // 동의여부
		, "duplicate" // 중복여부 (true:중복등록, false:신규등록)
		, "timestamp" // 등록일
}, alphabetic = true)
public class Hotdeals extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonProperty("event_id")
	private String eventId;

	@Getter
	@Setter
	@JsonProperty("event_type")
	private String eventType;

	@Getter
	@Setter
	@JsonProperty("phone_no")
	private String phoneNo;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private Boolean aggrement;

	@Getter
	@Setter
	@JsonProperty("duplicate_yn")
	private Boolean duplicate;

	@Getter
	@Setter
	@JsonProperty("close_yn")
	private Boolean close;

	@Getter
	@Setter
	@JsonProperty("date_from")
	private DateTime dateFrom;

	@Getter
	@Setter
	@JsonProperty("date_to")
	private DateTime dateTo;

	@Getter
	@Setter
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime timestamp;

	@Getter
	@Setter
	@JsonIgnore
	private int fcfsNum;

}
