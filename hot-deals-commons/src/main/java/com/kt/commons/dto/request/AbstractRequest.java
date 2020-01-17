/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kthcorp.commons.lang.BaseObject;

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
public abstract class AbstractRequest extends BaseObject {

	private static final long serialVersionUID = 1L;

	/**
	 * IP address of the client that sent the request
	 */
	@JsonIgnore
	private String remoteAddr;

	public AbstractRequest() {
		// ignore..
	}

	public AbstractRequest(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	/**
	 * IP address of the client that sent the request
	 *
	 * @return the remote address
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}

	/**
	 * IP address of the client that sent the request
	 *
	 * @param remoteAddr the remote address to set
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

}
