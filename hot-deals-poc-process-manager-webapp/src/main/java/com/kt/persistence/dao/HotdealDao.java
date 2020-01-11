package com.kt.persistence.dao;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.kt.dto.request.HotdealRequest;

@Repository
public class HotdealDao {

	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;

	public String getEventFcfs(String eventId, String phoneNum) {
		return hashOperations.get(eventId, phoneNum);
	}

	public boolean putEventFcfs(HotdealRequest params) {
		return hashOperations.putIfAbsent(params.getEventId(), params.getPhoneNum(), params.getName());
	}

}
