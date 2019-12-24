/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import com.kt.commons.dto.response.AbstractResponse.ParamError;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.lang.AbstractObject;
import com.kt.commons.web.util.ResponseUtils;
import com.kthcorp.commons.lang.StringUtils;
import com.kthcorp.commons.lang.http.HttpHeaders;
import com.kthcorp.commons.web.exception.ResponseException;

/**
 * Base Controller
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@Component
public abstract class AbstractController extends AbstractObject {

	private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

	public AbstractController() {
		// ignore...
	}

	/**
	 * 파라미터 에러 리턴.
	 *
	 * @param result the binding result
	 */
	protected void checkForErrors(final BindingResult result) {
		checkForErrors(result, null);
	}

	/**
	 * 파라미터 에러 리턴.
	 *
	 * @param result the binding result
	 * @param message the error message
	 */
	protected void checkForErrors(final BindingResult result, final String message) {
		if (result == null || !result.hasErrors()) {
			return; // 에러가 없으면 종료.
		}
		HttpStatus status = HttpStatus.PRECONDITION_FAILED;
		DefaultResponse res = new DefaultResponse(status.value(), getResponseMessage(status.value()));
		if (StringUtils.isNotBlank(message)) {
			res.setMessage(message);
		}
		for (FieldError error : result.getFieldErrors()) {
			res.add(new ParamError(error.getField(), error.getDefaultMessage()));
		}
		throw new ResponseException(res);
	}

	/**
	 * MediaType을 확인해서 Json 또는 ModelAndView 리턴.
	 *
	 * @param request the http servlet request
	 * @param viewName the view name
	 * @param messgae the result message.
	 * @return the default response or model and view
	 */
	protected Object result(final HttpServletRequest request, final String viewName, final String messgae) {
		if (log.isDebugEnabled()) {
			log.debug("Project Version:: {}", getApplicationInfo().getVersion());
		}
		String accept = request.getHeader(HttpHeaders.ACCEPT);
		if (StringUtils.startsWithIgnoreCase(accept, MediaType.APPLICATION_JSON_VALUE)) {
			HttpStatus status = HttpStatus.OK;
			DefaultResponse res = new DefaultResponse(status.value(), getResponseMessage(status.value()));
			return ResponseUtils.resultJson(request, res, status);
		} else {
			ModelAndView view = ResponseUtils.resultModelAndView(request, viewName, messgae);
			view.addObject("requestUrl", request.getRequestURL());
			return view;
		}
	}

	/**
	 * ModelAndView 리턴
	 *
	 * @param request the http servlet request
	 * @param viewName the view name
	 * @return the model and view
	 */
	protected ModelAndView resultView(final HttpServletRequest request, final String viewName) {
		ModelAndView view = ResponseUtils.resultModelAndView(request, viewName);
		view.addObject("requestUrl", request.getRequestURL());
		return view;
	}

	/**
	 * Json 리턴.
	 *
	 * @param request the http servlet request
	 * @return the object response entity
	 */
	protected ResponseEntity<Object> resultJson(final HttpServletRequest request) {
		return resultJson(request, null);
	}

	/**
	 * Json 리턴.
	 *
	 * @param request HttpServletRequest
	 * @param body the result body
	 * @return the object response entity
	 */
	protected ResponseEntity<Object> resultJson(final HttpServletRequest request, final Object body) {
		return resultJson(request, body, null);
	}

	/**
	 * Json 리턴.
	 *
	 * @param request the http servlet request
	 * @param messgae the result message
	 * @param body the result body
	 * @return the object response entity
	 */
	protected ResponseEntity<Object> resultJson(final HttpServletRequest request, final Object body,
			final String messgae) {
		HttpStatus status = HttpStatus.OK;
		if (body != null) {
			return ResponseUtils.resultJson(request, body, status);
		}
		DefaultResponse res = new DefaultResponse(status.value(), getResponseMessage(status.value()));
		if (StringUtils.isNotBlank(messgae)) {
			res.setMessage(messgae);
		}
		return ResponseUtils.resultJson(request, res, status);
	}

}
