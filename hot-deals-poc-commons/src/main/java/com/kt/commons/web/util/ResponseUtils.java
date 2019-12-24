/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.web.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.kt.commons.dto.response.DefaultResponse;
import com.kthcorp.commons.lang.StringUtils;
import com.kthcorp.commons.lang.http.HttpHeaders;

/**
 * HTTP Response 시 사용되는 유틸리티
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
public class ResponseUtils {

	/**
	 * URL Attribute Name
	 */
	public static final String URL_ATTRIBUTE_NAME = "url";

	/**
	 * Exception Attribute Name
	 */
	public static final String MESSAGE_ATTRIBUTE_NAME = "message";

	private ResponseUtils() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

	/**
	 * Json Rest Response.
	 *
	 * @param request the http servlet request
	 * @return the response entity
	 */
	public static final ResponseEntity<Object> resultJson(final HttpServletRequest request) {
		return resultJson(request, new DefaultResponse(), HttpStatus.OK, StandardCharsets.UTF_8);

	}

	/**
	 * Json Rest Response.
	 *
	 * @param request the http servlet request
	 * @param body the requeset body
	 * @return the response entity
	 */
	public static final ResponseEntity<Object> resultJson(final HttpServletRequest request, final Object body) {
		return resultJson(request, body, HttpStatus.OK, StandardCharsets.UTF_8);

	}

	/**
	 * Json Rest Response.
	 *
	 * @param request the http servlet request
	 * @param body the requeset body
	 * @param status the http status
	 * @return the response entity
	 */
	public static final ResponseEntity<Object> resultJson(final HttpServletRequest request, final Object body,
			final HttpStatus status) {
		return resultJson(request, body, status, StandardCharsets.UTF_8);

	}

	/**
	 * Json Rest Response.
	 *
	 * @param request the http servlet request
	 * @param ex the throwable
	 * @param status the http status
	 * @return the response entity
	 */
	public static final ResponseEntity<Object> resultJson(final HttpServletRequest request, final Throwable ex,
			final HttpStatus status) {
		DefaultResponse res = new DefaultResponse(status.value(), status.getReasonPhrase());
		if (StringUtils.isNotBlank(ex.getMessage())) {
			res.setMessage(ex.getMessage());
		}
		return resultJson(request, res, status, StandardCharsets.UTF_8);

	}

	/**
	 * Json Rest Response.
	 *
	 * @param request the http servlet request
	 * @param body the requeset body
	 * @param status the http status
	 * @param charSet the character set
	 * @return the response entity
	 */
	@SuppressWarnings("all")
	public static final ResponseEntity<Object> resultJson(final HttpServletRequest request, final Object body,
			final HttpStatus status, final Charset charSet) {
		HttpHeaders headers = new HttpHeaders();
		if (RequestUtils.isIE(request)) {
			headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		} else {
			headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=" + charSet.name());
		}

		if (body == null) {
			return new ResponseEntity<Object>(new DefaultResponse(), headers, status);
		}
		return new ResponseEntity<Object>(body, headers, status);
	}

	/**
	 * Model And View Response
	 *
	 * @param request the http servlet request
	 * @param viewName the view name
	 * @return the model and view
	 */
	public static final ModelAndView resultModelAndView(final HttpServletRequest request, final String viewName) {
		return resultModelAndView(request, viewName, null);
	}

	/**
	 * Model And View Response
	 *
	 * @param request the http servlet request
	 * @param viewName the view name
	 * @param body the object
	 * @return the model and view
	 */
	public static final ModelAndView resultModelAndView(final HttpServletRequest request, final String viewName,
			final Object body) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(URL_ATTRIBUTE_NAME, request.getRequestURL());
		if (body != null) {
			if (body instanceof Throwable) {
				Throwable ex = (Throwable) body;
				mav.addObject(MESSAGE_ATTRIBUTE_NAME, ex.getMessage());
			} else if (body instanceof String) {
				mav.addObject(MESSAGE_ATTRIBUTE_NAME, body);
			} else {
				mav.addObject(MESSAGE_ATTRIBUTE_NAME, body.toString());
			}
		}
		mav.setViewName(viewName);
		return mav;
	}

	/**
	 * JSON의 ContextType을 설정한다.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 */
	public static final void setJsonContentType(final HttpServletRequest request, final HttpServletResponse response) {
		setJsonContentType(request, response, StandardCharsets.UTF_8);
	}

	/**
	 * JSON의 ContextType을 설정한다.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param charSet the charset
	 */
	public static final void setJsonContentType(final HttpServletRequest request, final HttpServletResponse response,
			final Charset charSet) {
		if (RequestUtils.isIE(request)) {
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
		} else {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=" + charSet.name());
		}
	}

	/**
	 * Cookie 정보를 설정한다.
	 *
	 * @param response the servlet response
	 * @param server the server name
	 * @param name the cookie name
	 * @param value the cookie value
	 * @param path the cookie path
	 * @param maxAge the cookie max age
	 */
	public static final void setCookie(final ServletResponse response, final String server, final String name,
			final String value, String path, int maxAge) {
		HttpServletResponse httpRequest = (HttpServletResponse) response;
		setCookie(httpRequest, server, name, value, path, maxAge, false);
	}

	/**
	 * Cookie 정보를 설정한다.
	 *
	 * @param response the http servlet response
	 * @param server the server name
	 * @param name the cookie name
	 * @param value the cookie value
	 * @param path the cookie path
	 * @param maxAge the cookie max age
	 */
	public static final void setCookie(final HttpServletResponse response, final String server, final String name,
			final String value, final String path, final int maxAge) {
		setCookie(response, server, name, value, path, maxAge, false);
	}

	/**
	 * Cookie 정보를 설정한다.
	 *
	 * @param response the servlet response
	 * @param server the server name
	 * @param name the cookie name
	 * @param value the cookie value
	 * @param path the cookie path
	 * @param maxAge the cookie max age
	 * @param secure Cookie를 보안 프로토콜을 사용하여 보내야하는지 여부를 브라우저 노티
	 */
	public static final void setCookie(final ServletResponse response, final String server, final String name,
			final String value, final String path, final int maxAge, final boolean secure) {
		setCookie(response, server, name, value, path, maxAge, secure);
	}

	/**
	 * Cookie 정보를 설정한다.
	 *
	 * @param response the http servlet response
	 * @param server the server name
	 * @param name the cookie name
	 * @param value the cookie value
	 * @param path the cookie path
	 * @param maxAge the cookie max age
	 * @param secure Cookie를 보안 프로토콜을 사용하여 보내야하는지 여부를 브라우저 노티
	 */
	@SuppressWarnings("all")
	public static final void setCookie(final HttpServletResponse response, final String server, final String name,
			final String value, final String path, final int maxAge, final boolean secure) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(secure);
		cookie.setDomain(server);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * Redirect URL를 리턴한다.
	 *
	 * @param request the http servlet request
	 * @return the redirect url
	 */
	public static final String redirectUrl(final HttpServletRequest request) {
		String redirectUrl = RequestUtils.referer(request);
		if (StringUtils.isBlank(redirectUrl)) {
			redirectUrl = request.getContextPath();
		}
		return RequestUtils.requestServer(request) + StringUtils.replace(redirectUrl, "//", "/");
	}

	/**
	 * Redirect URL를 리턴한다.
	 *
	 * @param request the http servlet request
	 * @param url the redirect url
	 * @return redirect url
	 */
	public static final String redirectUrl(final HttpServletRequest request, final String url) {
		String redirectUrl = "/" + url;
		if (StringUtils.notEqualsAny(request.getContextPath(), "/")) {
			redirectUrl = request.getContextPath() + "/" + url;
		}
		return RequestUtils.requestServer(request) + StringUtils.replace(redirectUrl, "//", "/");
	}

	/**
	 * Allow Header를 설정한다.
	 *
	 * @param response the http servlet response
	 */
	public static final void setAllowHeaders(final HttpServletResponse response) {
		List<String> headerList = Lists.newArrayList();
		headerList.add(HttpHeaders.ACCEPT);
		headerList.add(HttpHeaders.ACCEPT_ENCODING);
		headerList.add(HttpHeaders.ACCEPT_LANGUAGE);
		headerList.add(HttpHeaders.AUTHORIZATION);
		headerList.add(HttpHeaders.CACHE_CONTROL);
		headerList.add(HttpHeaders.CONNECTION);
		headerList.add(HttpHeaders.CONTENT_TYPE);
		headerList.add(HttpHeaders.HOST);
		headerList.add(HttpHeaders.REFERER);
		headerList.add(HttpHeaders.USER_AGENT);
		headerList.add(HttpHeaders.ORIGIN);
		headerList.add("X-Requested-With");

		// Custom Header.
		headerList.add(HttpHeaders.AUTH_TOKEN);
		headerList.add(HttpHeaders.NS_CLIENT_IP);
		headerList.add(HttpHeaders.TID);

		response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, StringUtils.join(headerList, ", "));
		response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
		response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		response.addHeader("X-XSS-Protection", "0");
	}

}
