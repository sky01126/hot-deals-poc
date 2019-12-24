/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.lang.AbstractObject;
import com.kt.commons.web.util.ResponseUtils;
import com.kthcorp.commons.lang.StringUtils;
import com.kthcorp.commons.lang.http.HttpHeaders;

/**
 * 추상화된 기본 예외 처리 핸들러
 * {@link ResponseEntityExceptionHandler}
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
public abstract class AbstractExceptionHandler extends AbstractObject {

	private static final Logger log = LoggerFactory.getLogger(AbstractExceptionHandler.class);

	/**
	 * Error View page 설정.
	 *
	 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
	 * @version 1.0.0
	 * @since 8.0
	 */
	public static class ViewPage {

		public static final String ERROR = "error";

		public static final String ERROR_400 = "error/400";

		public static final String ERROR_401 = "error/401";

		public static final String ERROR_403 = "error/403";

		public static final String ERROR_404 = "error/404";

		public static final String ERROR_405 = "error/405";

		private ViewPage() {
			// ignore...
		}

	}

	/**
	 * HTTP Status 400 에러 처리.
	 *
	 * @param request the http servlet request
	 * @param error the throwable
	 * @return the object (ResponseEntity or ModelAndView)
	 */
	@ExceptionHandler({ MissingServletRequestParameterException.class, ServletRequestBindingException.class,
			TypeMismatchException.class, HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@SuppressWarnings("all")
	protected Object handleBadRequestException(final HttpServletRequest request, final Throwable error) {
		log.error("{} (URL={}, METHOD={})", error.getMessage(), request.getRequestURL(), request.getMethod());
		return result(request, HttpStatus.BAD_REQUEST, error, ViewPage.ERROR_400);
	}

	/**
	 * HTTP Status 404 에러 처리.
	 *
	 * @param request the http servlet request
	 * @param error the no handler found exception
	 * @return the object (ResponseEntity or ModelAndView)
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected Object handleNotFoundException(final HttpServletRequest request, final NoHandlerFoundException error) {
		log.error("{} (URL={}, METHOD={})", error.getMessage(), request.getRequestURL(), request.getMethod());
		return result(request, HttpStatus.NOT_FOUND, error, ViewPage.ERROR_404);
	}

	/**
	 * HTTP Status 405 에러 처리.
	 *
	 * @param request the http servlet request
	 * @param error the http request method not supported exception
	 * @return the object (ResponseEntity or ModelAndView)
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	protected Object handleNethodNotAllowedException(final HttpServletRequest request,
			final HttpRequestMethodNotSupportedException error) {
		log.error("{} (URL={}, METHOD={})", error.getMessage(), request.getRequestURL(), request.getMethod());
		// 지원되는 메소드를 보여준다.

		return result(request, HttpStatus.METHOD_NOT_ALLOWED, error, ViewPage.ERROR_405);
	}

	/**
	 * Internal Server Error
	 *
	 * @param request the http servlet request
	 * @param error the throwable
	 * @return the object (ResponseEntity or ModelAndView)
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected Object handleException(final HttpServletRequest request, final Throwable error) {
		log.error(error.getMessage(), error);
		return result(request, error);
	}

	/**
	 * Illegal Exception
	 *
	 * @param request the http servlet request
	 * @param error the runtime exception
	 * @return the response entity
	 */
	@ExceptionHandler({ IllegalStateException.class, IllegalArgumentException.class, NullPointerException.class })
	protected abstract Object handleRuntimeException(HttpServletRequest request, RuntimeException error);

	/**
	 * 리턴.
	 *
	 * @param request the http servlet request
	 * @param error the throwable
	 * @return the object
	 */
	protected Object result(final HttpServletRequest request, final Throwable error) {
		return result(request, HttpStatus.INTERNAL_SERVER_ERROR, error, null, ViewPage.ERROR);
	}

	/**
	 * 리턴.
	 *
	 * @param request the servlet request
	 * @param error the throwable
	 * @param viewName the view name
	 * @return the object
	 */
	protected Object result(final HttpServletRequest request, final String viewName, final Throwable error) {
		return result(request, HttpStatus.INTERNAL_SERVER_ERROR, error, null, viewName);
	}

	/**
	 * 리턴.
	 *
	 * @param request the http servlet request
	 * @param status the http status
	 * @param error the throwable
	 * @param viewName the view name
	 * @return the object
	 */
	protected Object result(final HttpServletRequest request, final HttpStatus status, final Throwable error,
			final String viewName) {
		return result(request, status, error, null, viewName);
	}

	/**
	 * 리턴.
	 *
	 * @param request the http servlet request
	 * @param status the http status
	 * @param error Throwable
	 * @param object Object
	 * @param viewName View Name
	 * @return the object
	 */
	protected Object result(final HttpServletRequest request, final HttpStatus status, final Throwable error,
			final Object object, final String viewName) {
		String accept = request.getHeader(HttpHeaders.ACCEPT);
		if (StringUtils.startsWithIgnoreCase(accept, MediaType.APPLICATION_JSON_VALUE)) {
			DefaultResponse res = new DefaultResponse(status.value(), getResponseMessage(status.value()));
			if (object != null) {
				res.setResultData(object);
			}
			return ResponseUtils.resultJson(request, res, status);
		} else {
			return ResponseUtils.resultModelAndView(request, viewName, error);
		}
	}

}
