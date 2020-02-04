package com.kt.commons.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.kthcorp.commons.web.annotation.RequestParamName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Component
public class HotdealsRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1, name = "event_id", value = "이벤트 아이디", example = "2020011301")
	@Getter
	@Setter
	@RequestParamName("event_id")
	// @NotEmpty(message = "이벤트 아이디는 필수 값입니다.")
	// @Size(min = 10, max = 10, message = "이벤트 아이디 10자리입니다.")
	private String eventId;

	@ApiModelProperty(position = 2, name = "phone_no", value = "핸드폰번호", example = "01012345678")
	@Getter
	@Setter
	@NotEmpty(message = "핸드폰번호는 필수 값입니다.")
	@Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "핸드폰번호 형식이 맞지 않습니다.")
	@RequestParamName("phone_no")
	private String phoneNo;

	@ApiModelProperty(position = 3, name = "name", value = "이름", example = "홍길동")
	@Getter
	@Setter
	@NotEmpty(message = "이름은 필수 값입니다.")
	private String name;

	@ApiModelProperty(position = 4, name = "aggrement", value = "동의여부", example = "true")
	@Getter
	@Setter
	private boolean aggrement;

	@ApiModelProperty(hidden = true)
	@Getter
	@Setter
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime timestamp = DateTime.now();

}
