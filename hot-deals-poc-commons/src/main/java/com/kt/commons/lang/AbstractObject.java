/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import com.kthcorp.commons.lang.StopWatch;
import com.kthcorp.commons.properties.ApplicationProperties;

/**
 * Abstract Property
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 2.1.0
 * @since 8.0
 */
@Component
public abstract class AbstractObject {

	private static final Logger log = LoggerFactory.getLogger(AbstractObject.class);

	/**
	 * 시간 측정을 위한 유틸.
	 */
	private StopWatch stopWatch;

	/**
	 * 어플리케이션 정보 Property.
	 */
	@Autowired
	private ApplicationProperties applicationPropertie;

	/**
	 * Message source accessor
	 */
	@Autowired
	private MessageSourceAccessor messageSource;

	/**
	 * Stop Watch를 새로 생성.
	 *
	 * @param message the message setting.
	 */
	protected void createStopWatch(final String message) {
		if (log.isDebugEnabled()) {
			if (this.stopWatch == null) {
				this.stopWatch = new StopWatch(message);
			} else {
				addStopWatch(message);
			}
		}
	}

	/**
	 * Stop Watch에 메시지 설정.
	 *
	 * @param message the message setting.
	 */
	protected void addStopWatch(final String message) {
		if (log.isDebugEnabled()) {
			if (this.stopWatch == null) {
				return;
			}
			if (this.stopWatch.isRunning()) {
				this.stopWatch.stop();
			}
			this.stopWatch.start(message);
		}
	}

	/**
	 * Stop Watch 정보 출력.
	 */
	protected void printStopWatch() {
		if (this.stopWatch == null) {
			return;
		}
		if (log.isDebugEnabled() && this.stopWatch.isRunning()) {
			this.stopWatch.stop();
			log.debug(this.stopWatch.prettyPrint());
		}
		stopWatch = null;
	}

	/**
	 * 어플리케이션 정보 Property.
	 *
	 * @return the application info property.
	 */
	protected ApplicationProperties getApplicationInfo() {
		return applicationPropertie;
	}

	/**
	 * Response 메시지 Property.
	 *
	 * @param status the response status.
	 * @return the response message.
	 */
	protected String getResponseMessage(final int status) {
		return messageSource.getMessage("message.response." + status);
	}

	/**
	 * Response 메시지 Property.
	 *
	 * @param status the response status.
	 * @param args arguments for the message, or {@code null} if none
	 * @return the response message.
	 */
	protected String getResponseMessage(final int status, final String... args) {
		return messageSource.getMessage("message.response." + status, args);
	}

}
