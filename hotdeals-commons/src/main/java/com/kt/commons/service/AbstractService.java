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

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import com.kt.commons.lang.AbstractObject;

/**
 * Abstract Service
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@Component
public abstract class AbstractService extends AbstractObject {

	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;

	public AbstractService() {
		// ignore...
	}

	/**
	 * 이벤트 중복 참여 체크.
	 *
	 * @param eventId 이벤트 아이디
	 * @param phoneNo 핸드폰번호
	 * @param name 이름
	 * @return true:중복, false:중복아님
	 */
	protected boolean duplicateCheck(String eventId, String phoneNo, String name) {
		return hashOperations.putIfAbsent(eventId, phoneNo, name);
	}

}
