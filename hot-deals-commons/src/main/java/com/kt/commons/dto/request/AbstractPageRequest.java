/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.kt.commons.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * 추상화된 Page Request DTO(Data Transfer Object)
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AbstractRequest
 * @since 8.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractPageRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 페이지 번호
	 */
	private int page;

	/**
	 * 한 페이지에 보여줄 ROW.
	 */
	private int size;

	public AbstractPageRequest() {
		// ignore...
	}

	public AbstractPageRequest(final int page, final int size) {
		this.page = page;
		this.size = size;
	}

	/**
	 * 페이지 번호
	 *
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 페이지 번호
	 *
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 한 페이지에 보여줄 ROW.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 한 페이지에 보여줄 ROW.
	 *
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

}
