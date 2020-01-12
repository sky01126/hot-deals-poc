package com.kt.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.kt.commons.dto.request.AbstractRequest;
import com.kthcorp.commons.web.annotation.RequestParamName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Component
public class HotdealRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "이벤트 아이디", example = "2020011301")
	@Getter
	@Setter
	@RequestParamName("event_id")
	@NotEmpty(message = "이벤트 아이디는 필수 값입니다.")
	private String eventId;

	@ApiModelProperty(value = "핸드폰번호", example = "01012345678")
	@Getter
	@Setter
	@NotEmpty(message = "핸드폰번호는 필수 값입니다.")
	@Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "핸드폰번호 형식이 맞지 않습니다.")
	@RequestParamName("phone_num")
	private String phoneNum;

	@ApiModelProperty(value = "이름", example = "홍길동")
	@Getter
	@Setter
	@NotEmpty(message = "이름은 필수 값입니다.")
	private String name;

	@Getter
	@Setter
	private boolean aggrement;

	@ApiModelProperty(hidden = true)
	@Getter
	@Setter
	private DateTime timestamp = DateTime.now();

}
