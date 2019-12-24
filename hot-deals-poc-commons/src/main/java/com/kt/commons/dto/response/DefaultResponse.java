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
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kt.commons.config.JsonName;

/**
 * Default Response DTO(Data Transfer Object)
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AbstractResponse
 * @since 8.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
@JsonPropertyOrder(value = { // 라인 정렬.
		JsonName.RESULT_CODE, // Result Code.
		JsonName.RESULT_MESSAGE, // Result Message.
		JsonName.TOTAL_PAGE, // Result Pagging Total Page.
		JsonName.TOTAL_SIZE, // Result Pagging Total Size.
		JsonName.NOW_PAGE, // Result Pagging Page.
		JsonName.NOW_PAGE_SIZE, // Result Pagging Size.
		JsonName.DATA, // Result Object Data.
		JsonName.LIST, // Result Object List.
		JsonName.ERRORS // Result Error List.
}, alphabetic = true)
@SuppressWarnings("all")
public class DefaultResponse extends AbstractResponse {

	private static final long serialVersionUID = 1L;

	/**
	 * 전체 페이지
	 */
	@JsonProperty(JsonName.TOTAL_PAGE)
	private Long totalPage;

	/**
	 * 전체 페이지 ROW.
	 */
	@JsonProperty(JsonName.TOTAL_SIZE)
	private Long totalSize;

	/**
	 * 현재 페이지
	 */
	@JsonProperty(JsonName.NOW_PAGE)
	private Long page;

	/**
	 * 한 페이지에 보여줄 ROW.
	 */
	@JsonProperty(JsonName.NOW_PAGE_SIZE)
	private Long size;

	/**
	 * Result Data
	 */
	@JsonProperty(JsonName.DATA)
	private Object resultData;

	/**
	 * Result List
	 */
	@JsonProperty(JsonName.LIST)
	private List<Object> resultList;

	/**
	 * Result Map
	 */
	@JsonIgnore
	private Map<String, Object> resultMap;

	/**
	 * Default Response
	 */
	public DefaultResponse() {
		// ignore..
	}

	/**
	 * Default Response
	 *
	 * @param code the result code
	 */
	public DefaultResponse(final int code) {
		super(code);
	}

	/**
	 * Default Response
	 *
	 * @param code the result code
	 * @param message the result message
	 */
	public DefaultResponse(final int code, final String message) {
		super(code, message);
	}

	/**
	 * Default Response
	 *
	 * @param map the result map
	 */
	public DefaultResponse(final Map<String, Object> map) {
		this.resultMap = map;
	}

	/**
	 * Default Response
	 *
	 * @param data the result data
	 */
	public DefaultResponse(final Object data) {
		this.resultData = data;
	}

	/**
	 * Default Response
	 *
	 * @param list the result list
	 */
	public DefaultResponse(final List<Object> list) {
		this.resultList = list;
	}

	/**
	 * Default Response
	 *
	 * @param data the result data
	 * @param list the result list
	 */
	public DefaultResponse(final Object data, final List<Object> list) {
		this.resultData = data;
		this.resultList = list;
	}

	/**
	 * Default Response
	 *
	 * @param map the result map
	 * @param data the result data
	 * @param list the result list
	 */
	public DefaultResponse(final Map<String, Object> map, final Object data, final List<Object> list) {
		this.resultMap = map;
		this.resultData = data;
		this.resultList = list;
	}

	/**
	 * 전체 페이지
	 *
	 * @return the total pate
	 */
	public Long getTotalPage() {
		return totalPage;
	}

	/**
	 * 전체 페이지
	 *
	 * @param totalPage the total page to set
	 */
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 전체 페이지 ROW.
	 *
	 * @return the total size
	 */
	public Long getTotalSize() {
		return totalSize;
	}

	/**
	 * 전체 페이지 ROW.
	 *
	 * @param totalSize the total size to set
	 */
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * 현재 페이지
	 *
	 * @return the now page number
	 */
	public Long getPage() {
		return page;
	}

	/**
	 * 현재 페이지
	 *
	 * @param page the now page number to set
	 */
	public void setPage(Long page) {
		this.page = page;
	}

	/**
	 * 한 페이지에 보여줄 ROW.
	 *
	 * @return the page size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * 한 페이지에 보여줄 ROW.
	 *
	 * @param size the page size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * Getting Result Data
	 *
	 * @return the result data
	 */
	public Object getResultData() {
		return resultData;
	}

	/**
	 * Setting Result Data
	 *
	 * @param data the result data to set
	 */
	public void setResultData(Object data) {
		this.resultData = data;
	}

	/**
	 * Getting Result List
	 *
	 * @return the result list
	 */
	public List<Object> getResultList() {
		return resultList;
	}

	/**
	 * Setting Result List
	 *
	 * @param list the result list to set
	 */
	public void setResultList(List<?> list) {
		this.resultList = (List<Object>) list;
	}

	/**
	 * ADD Result List
	 *
	 * @param obj the result list to add
	 */
	public void addResultList(Object obj) {
		if (this.resultList == null) {
			this.resultList = Lists.newArrayList();
		}
		this.resultList.add(obj);
	}

	/**
	 * Getting Result Map
	 *
	 * @return the result map
	 */
	@JsonAnyGetter
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	/**
	 * Setting Result Map
	 *
	 * @param map the result map to set
	 */
	public void setResultMap(Map<String, Object> map) {
		this.resultMap = map;
	}

	/**
	 * PUT Result Map
	 *
	 * @param key the result map to set key
	 * @param value the result map to set value
	 */
	public void putResultMap(String key, Object value) {
		if (this.resultMap == null) {
			this.resultMap = Maps.newLinkedHashMap();
		}
		this.resultMap.put(key, value);
	}

}
