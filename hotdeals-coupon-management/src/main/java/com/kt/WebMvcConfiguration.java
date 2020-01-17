/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt;

import java.util.List;

import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.kt.commons.AbstractWebMvcConfiguration;
import com.kthcorp.commons.enums.type.EnvTypes;
import com.kthcorp.commons.lang.StringUtils;
import com.kthcorp.commons.properties.ApplicationProperties;
import com.kthcorp.commons.web.annotation.RequestParamNameServletModelAttributeMethodProcessor;
import com.kthcorp.commons.web.listener.HttpSessionCheckingListener;

/**
 * Web Mvc Configuration.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AbstractWebMvcConfiguration
 * @since 8.0
 */
@EnableWebMvc
@ComponentScan
@Configuration
public class WebMvcConfiguration extends AbstractWebMvcConfiguration {

	private static final Logger log = LoggerFactory.getLogger(WebMvcConfiguration.class);

	/**
	 * 어플리케이션 정보 Property.
	 */
	@Autowired
	private ApplicationProperties applicationProperties;

	/**
	 * Locale change interceptor
	 */
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;

	/**
	 * Add resolvers to support custom controller method argument types.
	 *
	 * @param argumentResolvers initially an empty list
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		log.debug("Add Argument Resolvers...");
		argumentResolvers.add(new RequestParamNameServletModelAttributeMethodProcessor());
	}

	/**
	 * Add handlers to serve static resources such as images, js, and, css
	 * files from specific locations under web application root, the classpath,
	 * and others.
	 *
	 * @param registry InterceptorRegistry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.debug("Add Interceptors...");

		registry.addInterceptor(localeChangeInterceptor);
	}

	/**
	 * Add handlers to serve static resources such as images, js, and, css
	 * files from specific locations under web application root, the classpath,
	 * and others.
	 *
	 * @param registry ResourceHandlerRegistry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.debug("Add Resource Handlers...");
		super.addResourceHandlers(registry);

		// Static Resource 등록.
		registry.addResourceHandler("/static/**") // Resource Handler
				.addResourceLocations("classpath:/static/") // Resource Locations
				// .setCachePeriod(31556926); // Cache 기간 설정
				.resourceChain(true) // Resource Chain True
				.addResolver(new PathResourceResolver());

		// webjars를 Resource 등록.
		// webjars의 정확한 버전을 몰라도 사용할 수 있도록한다. ex. /webjars/jquery/jquery.min.js
		registry.addResourceHandler("/webjars/**") // Resource Handler
				.addResourceLocations("classpath:/META-INF/resources/webjars/") // Resource Locations
				// .setCachePeriod(31556926) // Cache 기간 설정
				.resourceChain(true) // Resource Chain True
				.addResolver(new WebJarsResourceResolver());

		// Cross Domain 등록.
		registry.addResourceHandler("/crossdomain.xml") // Resource Handler
				.addResourceLocations("/crossdomain.xml"); // Resource Locations
		// .setCachePeriod(31556926); // Cache 기간 설정

		// Swagger UI 등록.
		if (!StringUtils.equalsAnyIgnoreCase(applicationProperties.getEnv(), EnvTypes.PRODUCTION.toString())) {
			registry.addResourceHandler("swagger-ui.html") // Resource Handler
					.addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		}
	}

	/**
	 * Configure cross origin requests processing.
	 *
	 * @param registry the view resolver registry
	 * @since Spring Framework 4.1
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(viewResolver());
	}

	/**
	 * Configure View resolver to provide HTML output This is the default format in absence of any type suffix.
	 *
	 * @return ViewResolver the internal resource view resolver
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	/**
	 * Http Session Checking Listener 등록.
	 *
	 * @return the http session registration bean
	 */
	@Bean
	public ServletListenerRegistrationBean<HttpSessionListener> httpSessionCheckingListener() {
		log.debug("Http Session Checking Listener 등록...");
		ServletListenerRegistrationBean<HttpSessionListener> bean = new ServletListenerRegistrationBean<>();
		bean.setListener(new HttpSessionCheckingListener());
		return bean;
	}

}
