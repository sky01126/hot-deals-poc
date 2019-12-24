package com.kt.persistence.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kt.commons.config.JsonName;
import com.kt.commons.persistence.model.AbstractModel;

import lombok.Getter;
import lombok.Setter;

@Component
@JsonPropertyOrder(value = { // 라인 정렬.
		JsonName.SEQUENCE, // 일련번호.
		JsonName.USER_ID, // 사용자 아이디.
		JsonName.USER_NAME, // 사용자명.
		JsonName.USE_CODE, // 사용여부코드.
		JsonName.CURRENT_DATE, // 현재 시간.
		JsonName.UPDATE_DATE, // 최종 수정일.
		JsonName.CREATE_DATE // 최초 생성일.
}, alphabetic = true)
public class TestModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	/**
	 * User ID
	 */
	@Getter
	@Setter
	@JsonProperty(JsonName.USER_ID)
	private String userId;

	/**
	 * User name
	 */
	@Getter
	@Setter
	@JsonProperty(JsonName.USER_NAME)
	private String userName;

}
