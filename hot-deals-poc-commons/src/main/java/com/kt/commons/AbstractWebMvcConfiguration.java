/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons;

import java.nio.charset.StandardCharsets;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Abstract Web MVC Configuration
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see WebMvcConfigurer
 * @since 8.0
 */
public abstract class AbstractWebMvcConfiguration implements WebMvcConfigurer {

	private static final Logger log = LoggerFactory.getLogger(AbstractWebMvcConfiguration.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.setOrder(Integer.MIN_VALUE);
		registry.addResourceHandler("/favicon.ico") // Resource Handler
				.addResourceLocations("/") // Resource Locations
				.setCachePeriod(0); // Cache 기간 설정
	}

	/**
	 * 결과를 출력시에 강제로 UTF-8로 설정
	 *
	 * @return HttpMessageConverter
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		log.debug("Response Body Converter 등록...");
		return new StringHttpMessageConverter(StandardCharsets.UTF_8);
	}

	/**
	 * POST 요청시에 한글이 깨지는 문제 보완
	 *
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<Filter> characterEncodingFilter() {
		log.debug("Character Encoding Filter 등록...");
		final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		characterEncodingFilter.setForceEncoding(true);

		final FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(characterEncodingFilter);
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setOrder(Integer.MIN_VALUE);
		return registration;
	}

}
