package com.kt.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import com.kt.commons.dto.request.HotdealsRequest;
import com.kt.commons.persistence.model.Hotdeals;
import com.kt.commons.persistence.model.HotdealsEvent;
import com.kt.commons.persistence.model.HotdealsFcfs;
import com.kt.commons.service.AbstractService;
import com.kt.dto.request.HotdealsEventRequest;
import com.kthcorp.commons.lang.NumberUtils;
import com.kthcorp.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotdealsService extends AbstractService {

	@Autowired
<<<<<<< HEAD
	private KafkaTemplate<Object, Object> kafkaTemplate;
=======
	private HotdealsEventRepository hotdealsEventRepository;

	@Autowired
	private HotdealsFcfsRepository hotdealsFcfsRepository;

	// @Autowired
	// private KafkaTemplate<Object, Object> kafkaTemplate;
>>>>>>> cassandra

	@Resource(name = "standaloneStringRedisTemplate")
	private ListOperations<String, String> standaloneRedisListOperations;

	public String setEventInfo(HotdealsEventRequest params) {
		log.debug(params.toJsonPrettify());
		String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		HotdealsEvent event = save(prefix, params);

		// Redis에 Cache한다.
		Hotdeals hotdeals = new Hotdeals();
		hotdeals.setEventId(event.getEventId());
		hotdeals.setEventType(String.valueOf(event.getEventType()));
		hotdeals.setDateFrom(event.getDateFrom());
		hotdeals.setDateTo(event.getDateTo());
		hotdeals.setFcfsNum(event.getFcfsNum());
		setCache(hotdeals); // Redis Cache...

		// 이벤트 쿠폰번호를 생성한다.
		if (event.getFcfsNum() > 0) {
			crateCoupon(event, hotdeals);
		}
		return hotdeals.getEventId();
	}

	/**
	 * 선착순 쿠폰을 발급한다.
	 *
	 * @param params the hotdeals request
	 */
	public void setEventFcfsInfo(HotdealsRequest params) {
		log.info("선착순 요청 정보 : {}", params.toJsonLog());
		String couponNo = getCoupon(params.getEventId());
		if (StringUtils.isBlank(couponNo)) {
			// // Kafka에 완료 노티 후 종료한다.
			// log.info("선착순 완료.");
			// this.kafkaTemplate.send(Constants.KAFKA_TOPIC_HOTDEAL_FCFS_COUPON,
			// new HotdealsCoupon(params.getEventId(), true));
			return;
		}
		HotdealsFcfs fcfs = new HotdealsFcfs();
		fcfs.setPhoneNo(params.getPhoneNo());
		fcfs.setEventId(params.getEventId());
		fcfs.setName(params.getName());
		fcfs.setAgreement(params.isAggrement());
		fcfs.setFcfsNo(couponNo);
		fcfs.setTimestamp(params.getTimestamp());
		clusterRedisValueOperations.set(getRedisPickKey(fcfs.getEventId(), fcfs.getPhoneNo()), fcfs.toJson());
		log.debug(">>> {}", clusterRedisValueOperations.get(getRedisPickKey(fcfs.getEventId(), fcfs.getPhoneNo())));
	}

	private HotdealsEvent save(String prefix, HotdealsEventRequest params) {
		log.debug("prefix : {}", prefix);
		HotdealsEvent event = new HotdealsEvent();
		Set<String> keys = clusterStringRedisTemplate.keys(prefix + "*");
		if (keys.isEmpty()) {
			event.setEventId(prefix + "01");
		} else {
			List<String> list = new ArrayList<>(keys);
			Collections.sort(list, Collections.reverseOrder());
			String oldKey = list.get(0);
			long id = NumberUtils.toLong(oldKey, NumberUtils.toLong(prefix + "01"));
			event.setEventId(String.valueOf(id + 1));
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
		setEvent(event);
		return event;
	}

	private void crateCoupon(HotdealsEvent event, Hotdeals hotdeals) {
		for (int i = 0; i < event.getFcfsNum(); i++) {
			setCoupon(hotdeals.getEventId(), hotdeals.getEventId() + "-" + String.format("%010d", (i + 1)));
		}
	}

	private void setCoupon(String eventId, String value) {
		standaloneRedisListOperations.leftPush(getCouponKey(eventId), value);
	}

	private String getCoupon(String eventId) {
		return standaloneRedisListOperations.rightPop(getCouponKey(eventId));
	}

	protected Hotdeals getEventInfo(HotdealsEvent event) {
		Hotdeals hotdeals = new Hotdeals();
		hotdeals.setEventId(event.getEventId());
		return hotdeals;
	}

	private String getCouponKey(String eventId) {
		return String.join(":", "COUPON:", eventId);
	}

}
