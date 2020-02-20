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

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.kt.commons.config.Constants;
import com.kt.commons.lang.AbstractObject;
import com.kt.commons.persistence.model.Hotdeals;
import com.kt.commons.persistence.model.HotdealsEvent;

/**
 * Abstract Service
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@Component
public abstract class AbstractService extends AbstractObject {

	@Resource(name = "clusterStringRedisTemplate")
	protected StringRedisTemplate clusterStringRedisTemplate;

	@Resource(name = "standaloneRedisTemplate")
	protected ValueOperations<String, Object> standaloneRedisValueOperations;

	@Resource(name = "standaloneStringRedisTemplate")
	protected HashOperations<String, String, String> standaloneRedisHashOperations;

	@Resource(name = "clusterStringRedisTemplate")
	protected ValueOperations<String, String> clusterRedisValueOperations;

	public AbstractService() {
		// ignore...
	}

<<<<<<< HEAD
	/**
	 * JSON String를 Object로 변환.
	 *
	 * @param <T> This is the type parameter.
	 * @param json 변환할 json object.
	 * @param clazz 변환할 Object class.
	 * @return th object.
	 * @throws IOException in case of I/O errors in case of I/O errors.
	 */
	public <T> Object jsonToObject(final String json, final Class<T> clazz) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		return mapper.readValue(json, clazz);
	}

	protected String getRedisPickKey(String eventId, String phoneNo) {
		return getRedisKey("PICK", eventId, phoneNo);

	}

	protected String getRedisFcfsKey(String eventId, String phoneNo) {
		return getRedisKey("FCFS", eventId, phoneNo);

	}

	private String getRedisKey(String prefix, String eventId, String phoneNo) {
		return String.join(":", prefix, eventId, phoneNo);

=======
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
>>>>>>> cassandra
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
		return standaloneRedisHashOperations.putIfAbsent(eventId, phoneNo, name);
	}

	protected void setCache(Hotdeals hotdeals) {
		standaloneRedisValueOperations.set(Constants.HOTDEALS_REDIS_CACHE_KEY, hotdeals);
	}

	protected Hotdeals getCache() {
		return (Hotdeals) standaloneRedisValueOperations.get(Constants.HOTDEALS_REDIS_CACHE_KEY);
	}

	protected void setEvent(HotdealsEvent event) {
		clusterRedisValueOperations.set(event.getEventId(), event.toJson());
	}

}
