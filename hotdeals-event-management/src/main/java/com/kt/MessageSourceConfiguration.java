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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Message Source Configuration
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see CachingConfigurerSupport
 * @since 8.0
 */
@Configuration
public class MessageSourceConfiguration {

	@Value("${spring.messages.always-use-message-format}")
	private boolean alwaysUseMessageFormat;

	@Value("${spring.messages.basename}")
	private String messagesBasename;

	@Value("${spring.messages.encoding}")
	private String messagesEncoding;

	/**
	 * Reloadable resource bundle message source
	 *
	 * @return the reloadable resource bundle message source
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setAlwaysUseMessageFormat(alwaysUseMessageFormat);
		messageSource.setBasename(messagesBasename); // "i18n/message"
		messageSource.setDefaultEncoding(messagesEncoding);
		return messageSource;
	}

	@Bean
	public MessageSourceAccessor getMessageSourceAccessor() {
		ReloadableResourceBundleMessageSource messageSource = messageSource();
		return new MessageSourceAccessor(messageSource);
	}

	/**
	 * Local resolver
	 * "Locale change interceptor"에 한번 설정된 Locale은 Sesson 또는 Cookie에 저장된다.
	 *
	 * @return the locale resolver
	 */
	@Bean
	@SuppressWarnings("all")
	public LocaleResolver localeResolver() {
		// 세션을 사용한 예제
		// SessionLocaleResolver resolver = new SessionLocaleResolver();
		// // Default Locale을 설정하지 않으면 Header의 "Accept-Language"로 설정 할 수 있음.
		// // resolver.setDefaultLocale(Locale.KOREAN);
		// return resolver;

		// 쿠키를 사용한 예제
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		// Default Locale을 설정하지 않으면 Header의 "Accept-Language"로 설정 할 수 있음.
		// resolver.setDefaultLocale(Locale.KOREAN);
		resolver.setCookieName("locale");
		return resolver;
	}

	/**
	 * Locale change interceptor
	 *
	 * @return the local change interceptor
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("locale");
		return lci;
	}

}
