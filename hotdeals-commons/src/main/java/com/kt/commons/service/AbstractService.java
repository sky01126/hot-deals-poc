/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.service;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.kt.commons.config.Constants;
import com.kt.commons.lang.AbstractObject;
import com.kt.commons.persistence.model.Hotdeals;
import com.kt.commons.persistence.model.HotdealsEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * Abstract Service
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@Slf4j
@Component
public abstract class AbstractService extends AbstractObject {

	protected static boolean fcfsClosed = false;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, Object> valueOperations;

	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;

	public AbstractService() {
		// ignore...
	}

	protected Hotdeals getEventInfo(HotdealsEvent event) {
		// log.debug(event.toJsonLog());
		// Hotdeals hotdeals = getCache();
		// if (hotdeals != null) {
		// return hotdeals;
		// }
		Hotdeals hotdeals = null;
		DateTime nowDateTime = DateTime.now();
		log.debug(">>> {}, {}", event.getDateFrom().isEqual(nowDateTime), event.getDateTo().isEqual(nowDateTime));
		log.debug(">>> {}, {}", event.getDateFrom().isBefore(nowDateTime), event.getDateTo().isAfter(nowDateTime));
		if (event.getDateFrom().isEqual(nowDateTime) || event.getDateTo().isEqual(nowDateTime)
				|| (event.getDateFrom().isBefore(nowDateTime) && event.getDateTo().isAfter(nowDateTime))) {
			hotdeals = new Hotdeals();
			hotdeals.setEventId(event.getKey().getEventId());
			hotdeals.setEventType(String.valueOf(event.getEventType()));
			hotdeals.setDateFrom(event.getDateFrom());
			hotdeals.setDateTo(event.getDateTo());
			hotdeals.setFcfsNum(event.getFcfsNum());
			setCache(hotdeals); // Redis Cache...
		}
		return hotdeals;
	}

	/**
	 * 이벤트 중복 참여 체크.
	 *
	 * @param eventId 이벤트 아이디
	 * @param phoneNo 핸드폰번호
	 * @param name 이름
	 * @return true:중복아님, false:중복
	 */
	protected boolean duplicateCheck(String eventId, String phoneNo, String name) {
		return hashOperations.putIfAbsent(eventId, phoneNo, name);
	}

	protected void setCache(Hotdeals hotdeals) {
		valueOperations.set(Constants.HOTDEALS_REDIS_CACHE_KEY, hotdeals);
	}

	protected Hotdeals getCache() {
		return (Hotdeals) valueOperations.get(Constants.HOTDEALS_REDIS_CACHE_KEY);
	}

}
