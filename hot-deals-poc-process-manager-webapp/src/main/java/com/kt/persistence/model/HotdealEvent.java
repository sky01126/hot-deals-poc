package com.kt.persistence.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kt.commons.persistence.model.AbstractModel;

import lombok.Getter;
import lombok.Setter;

@Component
@JsonPropertyOrder(value = { // 라인 정렬
		"event_id" // 이벤트 아이디
		, "phone_num" // 핸드폰번호
		, "name" // 이름
		, "aggrement" // 동의여부
		, "duplicate" // 중복여부 (true:중복등록, false:신규등록)
		, "timestamp" // 등록일
}, alphabetic = true)
public class HotdealEvent extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonProperty("event_id")
	private String eventId;

	@Getter
	@Setter
	@JsonProperty("phone_num")
	private String phoneNum;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private Boolean aggrement;

	@Getter
	@Setter
	private Boolean duplicate;

	@Getter
	@Setter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime timestamp;

}
