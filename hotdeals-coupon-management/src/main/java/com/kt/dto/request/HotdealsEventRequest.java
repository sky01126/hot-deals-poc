package com.kt.dto.request;

import javax.validation.constraints.NotEmpty;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.kt.commons.dto.request.AbstractRequest;
import com.kthcorp.commons.web.annotation.RequestParamName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Component
public class HotdealsEventRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "이벤트명", example = "테스트")
	@Getter
	@Setter
	@NotEmpty(message = "이벤트명은 필수 값입니다.")
	@RequestParamName("event_name")
	private String eventName;

	@ApiModelProperty(value = "선착순", example = "100")
	@Getter
	@Setter
	@RequestParamName("fcfs_num")
	private Integer fcfsNum;

	@ApiModelProperty(hidden = true)
	@Getter
	@Setter
	@RequestParamName("date_from")
	private DateTime dateFrom = DateTime.now();

	@ApiModelProperty(hidden = true)
	@Getter
	@Setter
	@RequestParamName("date_to")
	private DateTime dateTo = DateTime.now().plusDays(100);

}
