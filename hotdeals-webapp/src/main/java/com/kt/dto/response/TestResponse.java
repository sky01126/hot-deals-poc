/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.dto.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kt.commons.config.JsonName;
import com.kt.commons.dto.response.AbstractResponse;
import com.kt.persistence.model.TestModel;

/**
 * Validatoin Response
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AbstractResponse
 * @since 8.0
 */
@Component
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = { // 라인 정렬.
		JsonName.RESULT_CODE, // Result Code.
		JsonName.RESULT_MESSAGE, // Result Message.
		"data", // Result Data.
		JsonName.ERRORS // Result Error List.
}, alphabetic = true)
public class TestResponse extends AbstractResponse {

	private static final long serialVersionUID = 1L;

	/**
	 * Validation Info
	 */
	@JsonProperty("data")
	private TestModel data;

	/**
	 * Validatoin Response
	 */
	public TestResponse() {
		// ignore..
	}

	/**
	 * Validatoin Response
	 *
	 * @param code Result Code
	 * @param message Result Message
	 */
	public TestResponse(final int code, final String message) {
		super(code, message);
	}

	/**
	 * Validation Info
	 *
	 * @return the data
	 */
	public TestModel getData() {
		return data;
	}

	/**
	 * Validation Info
	 *
	 * @param data the data to set
	 */
	public void setData(TestModel data) {
		this.data = data;
	}

}
