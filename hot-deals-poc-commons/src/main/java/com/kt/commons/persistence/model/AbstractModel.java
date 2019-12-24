/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.persistence.model;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.kt.commons.config.JsonName;
import com.kthcorp.commons.lang.BaseObject;

/**
 * Base Model
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see BaseObject
 * @since 8.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractModel extends BaseObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Environment
	 */
	@JsonIgnore
	private String env;

	/**
	 * 일련번호.
	 */
	@JsonProperty(JsonName.SEQUENCE)
	private Long seq;

	/**
	 * LIMIT의 시작 위치.
	 */
	@JsonProperty("offset")
	private Integer offset;

	/**
	 * 조회할 ROW의 숫자.
	 */
	@JsonProperty("size")
	private Integer size;

	/**
	 * 사용 여부.
	 */
	@JsonProperty(JsonName.USE_CODE)
	private String useCode;

	/**
	 * 현재 시간.
	 */
	@JsonProperty(JsonName.CURRENT_DATE)
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime currentDate;

	/**
	 * 최종 수정일
	 */
	@JsonProperty(JsonName.UPDATE_DATE)
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime updateDate;

	/**
	 * 최초 등록일
	 */
	@JsonProperty(JsonName.CREATE_DATE)
	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private DateTime createDate;

	/**
	 * Environment
	 *
	 * @return the env
	 */
	public String getEnv() {
		return env;
	}

	/**
	 * Environment
	 *
	 * @param env the env to set
	 */
	public void setEnv(final String env) {
		this.env = env;
	}

	/**
	 * 일련번호.
	 *
	 * @return the seq
	 */
	public Long getSeq() {
		return seq;
	}

	/**
	 * 일련번호.
	 *
	 * @param seq the seq to set
	 */
	public void setSeq(final Long seq) {
		this.seq = seq;
	}

	/**
	 * LIMIT의 시작 위치.
	 *
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * LIMIT의 시작 위치.
	 *
	 * @param offset the offset to set
	 */
	public void setOffset(final Integer offset) {
		this.offset = offset;
	}

	/**
	 * 조회할 ROW의 숫자.
	 *
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * 조회할 ROW의 숫자.
	 *
	 * @param size the size to set
	 */
	public void setSize(final Integer size) {
		this.size = size;
	}

	/**
	 * 사용 여부.
	 *
	 * @return the useCode
	 */
	public String getUseCode() {
		return useCode;
	}

	/**
	 * 사용 여부.
	 *
	 * @param useCode the useCode to set
	 */
	public void setUseCode(final String useCode) {
		this.useCode = useCode;
	}

	/**
	 * 현재 시간.
	 *
	 * @return the currentDate
	 */
	public DateTime getCurrentDate() {
		return currentDate;
	}

	/**
	 * 현재 시간.
	 *
	 * @param currentDate the currentDate to set
	 */
	public void setCurrentDate(final DateTime currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * 최종 수정일
	 *
	 * @return the updateDate
	 */
	public DateTime getUpdateDate() {
		return updateDate;
	}

	/**
	 * 최종 수정일
	 *
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(final DateTime updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 최초 등록일
	 *
	 * @return the createDate
	 */
	public DateTime getCreateDate() {
		return createDate;
	}

	/**
	 * 최초 등록일
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(final DateTime createDate) {
		this.createDate = createDate;
	}

}
