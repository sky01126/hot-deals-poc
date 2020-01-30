package com.kt.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import com.kt.commons.persistence.model.Hotdeals;
import com.kt.commons.persistence.model.HotdealsEvent;
import com.kt.commons.persistence.model.HotdealsEventKey;
import com.kt.commons.persistence.repositories.HotdealsEventRepository;
import com.kt.commons.persistence.repositories.HotdealsFcfsRepository;
import com.kt.commons.service.AbstractService;
import com.kt.dto.request.HotdealsEventRequest;
import com.kthcorp.commons.lang.NumberUtils;
import com.kthcorp.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotdealsService extends AbstractService {

	@Autowired
	private HotdealsEventRepository hotdealsEventRepository;

	@Autowired
	private HotdealsFcfsRepository hotdealsFcfsRepository;

	@Resource(name = "stringRedisTemplate")
	private ListOperations<String, String> listOperations;

	public String setEventInfo(HotdealsEventRequest params) {
		log.debug(params.toJsonPrettify());
		String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		HotdealsEvent event = save(prefix, params);

		// Redis에 Cache한다.
		Hotdeals hotdeals = new Hotdeals();
		hotdeals.setEventId(event.getKey().getEventId());
		hotdeals.setEventType(String.valueOf(event.getEventType()));
		hotdeals.setFcfsNum(event.getFcfsNum());
		setCache(hotdeals); // Redis Cache...

		// 이벤트 쿠폰번호를 생성한다.
		if (event.getFcfsNum() > 0) {
			crateCoupon(event, hotdeals);
		}
		return hotdeals.getEventId();
	}

	private HotdealsEvent save(String prefix, HotdealsEventRequest params) {
		HotdealsEvent event = new HotdealsEvent();
		List<HotdealsEvent> list = hotdealsEventRepository.findAll();
		if (!list.isEmpty()) {
			log.debug("1");
			list.sort(new HotdealsEventSorter());
			HotdealsEvent hotdealsEvent = list.get(0);
			if (hotdealsEvent != null && StringUtils.startsWith(hotdealsEvent.getKey().getEventId(), prefix)) {
				log.debug("2");
				long id = NumberUtils.toLong(hotdealsEvent.getKey().getEventId(), NumberUtils.toLong(prefix + "01"));
				event.setKey(new HotdealsEventKey(String.valueOf(id + 1)));
			} else {
				log.debug("3");
				event.setKey(new HotdealsEventKey(prefix + "01"));
			}
		} else {
			log.debug("4");
			event.setKey(new HotdealsEventKey(prefix + "01"));
		}

		event.setEventName(params.getEventName());

		if (params.getFcfsNum() > 0) {
			event.setEventType("3");
			event.setFcfsNum(params.getFcfsNum());
		} else {
			event.setEventType("2");
		}

		event.setDateFrom(params.getDateFrom());
		event.setDateTo(params.getDateTo());
		log.debug(event.toJsonPrettify());
		hotdealsEventRepository.save(event);
		return event;
	}

	private void crateCoupon(HotdealsEvent event, Hotdeals hotdeals) {
		for (int i = 0; i < event.getFcfsNum(); i++) {
			listOperations.leftPush(getCouponKey(hotdeals.getEventId()),
					hotdeals.getEventId() + "-" + String.format("%010d", i));
		}
	}

	@Override
	protected Hotdeals getEventInfo(HotdealsEvent event) {
		Hotdeals hotdeals = new Hotdeals();
		hotdeals.setEventId(event.getKey().getEventId());
		return hotdeals;
	}

	private String getCouponKey(String eventId) {
		return "COUPON." + eventId;
	}

	public static class HotdealsEventSorter implements Comparator<HotdealsEvent> {

		@Override
		public int compare(HotdealsEvent o1, HotdealsEvent o2) {
			return o2.getKey().getEventId().compareTo(o1.getKey().getEventId());
		}

	}

}
