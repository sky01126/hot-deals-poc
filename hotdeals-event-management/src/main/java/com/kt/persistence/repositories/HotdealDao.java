package com.kt.persistence.repositories;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.kt.commons.dto.request.HotdealRequest;

@Repository
public class HotdealDao {

	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;

	public String getEventFcfs(String eventId, String phoneNo) {
		return hashOperations.get(eventId, phoneNo);
	}

	public boolean putEventFcfs(HotdealRequest params) {
		return hashOperations.putIfAbsent(params.getEventId(), params.getPhoneNo(), params.getName());
	}

}
