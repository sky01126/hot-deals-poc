/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.commons.dto.response.AbstractResponse.ParamError;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.web.handler.AbstractExceptionHandler;
import com.kt.commons.web.util.ResponseUtils;
import com.kthcorp.commons.lang.StringUtils;
import com.kthcorp.commons.web.exception.ResponseException;

/**
 * Global Controller 예외 처리.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AbstractExceptionHandler
 * @since 8.0
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler extends AbstractExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@Override
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected Object handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
		if (log.isErrorEnabled()) {
			log.error(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex);
		}
		return result(request, "error", ex);
	}

	/**
	 * Parameter Bind Errror.
	 *
	 * @param request HttpServletRequest
	 * @param ex BindException
	 * @return Object (ResponseEntity or ModelAndView)
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	protected Object handleBindException(HttpServletRequest request, BindException ex) {
		if (log.isErrorEnabled()) {
			log.error(ex.getMessage());
		}
		String accept = request.getHeader(HttpHeaders.ACCEPT);
		if (StringUtils.startsWithIgnoreCase(accept, MediaType.APPLICATION_JSON_VALUE)) {
			int status = HttpStatus.PRECONDITION_FAILED.value();
			DefaultResponse res = new DefaultResponse(status, getResponseMessage(status));
			for (FieldError error : ex.getBindingResult().getFieldErrors()) {
				res.add(new ParamError(error.getField(), error.getDefaultMessage()));
			}
			return ResponseUtils.resultJson(request, res);
		} else {
			return ResponseUtils.resultModelAndView(request, "error/400", ex);
		}
	}

	/**
	 * 지원하지 않는 미디어 타입 에러.
	 *
	 * @param request HttpServletRequest
	 * @param ex HttpMediaTypeNotSupportedException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
			HttpMediaTypeNotSupportedException ex) {
		if (log.isErrorEnabled()) {
			log.error(ex.getMessage(), ex);
		}
		DefaultResponse res = new DefaultResponse(HttpStatus.NOT_ACCEPTABLE.value(),
				getResponseMessage(HttpStatus.NOT_ACCEPTABLE.value()));
		return ResponseUtils.resultJson(request, res, HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Response Exception
	 *
	 * @param request HttpServletRequest
	 * @param ex ResponseException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(ResponseException.class)
	protected ResponseEntity<Object> handleResponseException(HttpServletRequest request, ResponseException ex) {
		if (log.isErrorEnabled()) {
			log.error(ex.getMessage());
		}
		if (ex.getObject() != null) {
			return ResponseUtils.resultJson(request, ex.getObject(), ex.getStatus());
		} else {
			DefaultResponse res = new DefaultResponse(ex.getCode());
			if (StringUtils.isNotBlank(ex.getMessage())) {
				res.setMessage(ex.getMessage());
			} else {
				res.setMessage(getResponseMessage(ex.getCode()));
			}
			return ResponseUtils.resultJson(request, res, ex.getStatus());
		}
	}

}
