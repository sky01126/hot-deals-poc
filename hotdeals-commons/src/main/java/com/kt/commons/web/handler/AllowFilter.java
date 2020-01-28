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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kt.commons.web.util.RequestUtils;
import com.kt.commons.web.util.ResponseUtils;
import com.kthcorp.commons.constants.CommonConstants;
import com.kthcorp.commons.lang.StringUtils;
import com.kthcorp.commons.lang.http.HttpHeaders;

/**
 * HTTP 접근 제어 Filter - Cross-Origin Resource Sharing (CORS)
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see OncePerRequestFilter
 * @since 8.0
 */
public class AllowFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(AllowFilter.class);

	/**
	 * Same contract as for {@code doFilter}, but guaranteed to be
	 * just invoked once per request within a single request thread.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param chain the filter chain
	 * @throws ServletException the servlet exception
	 * @throws IOException the io exception
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		String filterName = getClass().getName() + ALREADY_FILTERED_SUFFIX;
		String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
		log.debug("Already Filtered Attribute Name: {}", alreadyFilteredAttributeName);

		// Just REPLY OK if request method is OPTIONS for CORS (pre-flight)
		if (StringUtils.equalsAnyIgnoreCase(request.getMethod(), HttpOptions.METHOD_NAME)) {
			ResponseUtils.setAllowHeaders(response);
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		} else if (RequestUtils.isNotResource(request.getRequestURI())
				&& (StringUtils.isBlank(alreadyFilteredAttributeName)
						|| StringUtils.notEquals(filterName, alreadyFilteredAttributeName))) {
			boolean isFirstRequest = !isAsyncDispatch(request);

			// debug 모드에서는 Request 정보를 로깅한다.
			if (isFirstRequest && log.isDebugEnabled()) {
				log.debug("Allow Filter...\n{}", getRequestInfo(request));
			}
			String accept = request.getHeader(HttpHeaders.ACCEPT);
			if (StringUtils.startsWithIgnoreCase(accept, MediaType.APPLICATION_JSON_VALUE)) {
				ResponseUtils.setAllowHeaders(response);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * Request Log
	 *
	 * @param request the http servlet request
	 * @return the log stack
	 */
	private String getRequestInfo(final HttpServletRequest request) {
		StringBuilder logstack = new StringBuilder();
		logstack.append("+").append(CommonConstants.line(20)).append("\n");
		logstack.append("|").append(" Method             | ").append(request.getMethod()).append("\n");
		logstack.append("|").append(" Accept             | ")
				.append(StringUtils.defaultString(request.getHeader(HttpHeaders.ACCEPT), "-")).append("\n");
		logstack.append("|").append(" Accept-Language    | ")
				.append(StringUtils.defaultString(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE), "-")).append("\n");
		logstack.append("|").append(" Content-Type       | ")
				.append(StringUtils.defaultString(request.getContentType(), "-")).append("\n");
		logstack.append("|").append(" User-Agent         | ")
				.append(StringUtils.defaultString(request.getHeader(HttpHeaders.USER_AGENT), "-")).append("\n");
		logstack.append("|").append(" Remote Address     | ").append(request.getRemoteAddr()).append("\n");
		logstack.append("|").append(" Request URL        | ").append(request.getRequestURL()).append("\n");

		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			logstack.append("|").append(" QueryString        | ").append(queryString).append("\n");
		}

		String tid = request.getHeader(HttpHeaders.TID);
		if (StringUtils.isNotBlank(tid)) {
			logstack.append("|").append(" Transaction-ID     | ").append(tid).append("\n");
		}

		String authToken = request.getHeader(HttpHeaders.AUTH_TOKEN);
		if (StringUtils.isNotBlank(authToken)) {
			logstack.append("|").append(" X-Auth-Token       | ").append(authToken).append("\n");
		}

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isNotBlank(authorization)) {
			logstack.append("|").append(" Authorization      | ").append(authorization).append("\n");
		}

		String forwardedHost = request.getHeader("X-Forwarded-Host");
		if (StringUtils.isNotBlank(forwardedHost)) {
			logstack.append("|").append(" X-Forwarded-Host   | ").append(forwardedHost).append("\n");
		}

		String forwardedFor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(forwardedFor)) {
			logstack.append("|").append(" X-Forwarded-For    | ").append(forwardedFor).append("\n");
		}

		String clientIp = request.getHeader(HttpHeaders.NS_CLIENT_IP);
		if (StringUtils.isNotBlank(clientIp)) {
			logstack.append("|").append(" NS-Client-IP       | ").append(clientIp).append("\n");
		}
		logstack.append("+").append(CommonConstants.line(20));
		return logstack.toString();
	}

}
