/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Lists;
import com.kt.commons.config.JsonName;
import com.kthcorp.commons.lang.BaseObject;
import com.kthcorp.commons.lang.StringUtils;

/**
 * 추상화된 Base Request DTO(Data Transfer Object)
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see BaseObject
 * @since 8.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractResponse extends BaseObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Result Code
	 */
	@JsonProperty(JsonName.RESULT_CODE)
	protected int code = 200;

	/**
	 * Result Message
	 */
	@JsonProperty(JsonName.RESULT_MESSAGE)
	protected String message = "SUCCESS";

	/**
	 * Result Error Messages
	 */
	@JsonProperty(JsonName.ERRORS)
	private List<ParamError> errors;

	public AbstractResponse() {
		// ignore...
	}

	/**
	 * 추상화된 Base Request DTO
	 *
	 * @param code the result code
	 */
	public AbstractResponse(final int code) {
		this.code = code;
	}

	/**
	 * 추상화된 Base Request DTO
	 *
	 * @param code the result code
	 * @param message the result message
	 */
	public AbstractResponse(final int code, final String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Result Code
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Result Code
	 *
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Result Message
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Result Message
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Error 메시지를 ADD한다.
	 *
	 * @param error ParamError
	 */
	public void add(ParamError error) {
		if (errors == null) {
			this.errors = Lists.newArrayList();
		}
		this.errors.add(error);
	}

	/**
	 * Result Error Messages
	 *
	 * @return the errors
	 */
	public List<ParamError> getErrors() {
		return errors;
	}

	/**
	 * Result Error Messages
	 *
	 * @param errors the errors to set
	 */
	public void setErrors(List<ParamError> errors) {
		this.errors = errors;
	}

	/**
	 * 파리미터 에러 정보
	 *
	 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
	 * @version 1.0.0
	 * @see BaseObject
	 * @since 8.0
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ParamError extends BaseObject {

		private static final long serialVersionUID = 1L;

		/**
		 * 파라미터명.
		 */
		@JsonProperty("name")
		@JsonInclude(Include.NON_NULL)
		private final String name;

		/**
		 * 에러메시지.
		 */
		@JsonProperty("value")
		@JsonInclude(Include.NON_NULL)
		private final String value;

		/**
		 * 파리미터 에러 정보
		 *
		 * @param name 파라미터명.
		 * @param value 에러메시지.
		 */
		public ParamError(String name, String value) {
			this.name = StringUtils.convertCamelcaseToUnderscore(name, false);
			this.value = value;
		}

		/**
		 * 파리미터 에러 정보
		 *
		 * @param name 파라미터명.
		 * @param value 에러메시지.
		 * @param convert the camelcase to underscore
		 */
		public ParamError(String name, String value, boolean convert) {
			if (convert) {
				this.name = StringUtils.convertCamelcaseToUnderscore(name);
			} else {
				this.name = name;
			}
			this.value = value;
		}

		/**
		 * 파라미터명.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * 에러메시지.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

	}

}
