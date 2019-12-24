package com.kt.dto.request;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.kt.commons.dto.request.AbstractRequest;

import lombok.Getter;
import lombok.Setter;

@Component
public class TestRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@NotEmpty(message = "아이디 파라미터는 필수 값입니다.")
	private String id;

	@Getter
	@Setter
	@NotEmpty(message = "Name 파라미터는 필수 값입니다.")
	private String name;

}
