package com.kt.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.commons.dto.request.HotdealRequest;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.persistence.model.Hotdeals;
import com.kt.commons.service.AbstractService;
import com.kt.persistence.model.HotdealsEvent;
import com.kt.persistence.model.HotdealsPick;
import com.kt.persistence.model.HotdealsPickKey;
import com.kt.persistence.repositories.HotdealsEventRepository;
import com.kt.persistence.repositories.HotdealsPickRepository;
import com.kthcorp.commons.lang.NumberUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotdealsService extends AbstractService {

	private static Hotdeals sHotdeals = null;

	@Autowired
	private HotdealsEventRepository hotdealsEventRepository;

	@Autowired
	private HotdealsPickRepository hotdealsPickRepository;

	/**
	 * 이벤트 기본 정보 조회.
	 */
	public DefaultResponse getEventInfo() {
		List<HotdealsEvent> list = hotdealsEventRepository.findAll();
		if (!list.isEmpty()) {
			HotdealsEvent event = null;
			for (int i = 0; i < list.size(); i++) {
				event = list.get(i);
				DefaultResponse res = getEventInfo(event);
				if (res != null) {
					return res;
				}
			}

			// 현재시간 이후에 진행 될 예정의 이벤트가 있으면 "이벤트 준비중"
			if (event != null && event.getDateFrom().isAfter(LocalDateTime.now())) {
				return new DefaultResponse(511, getResponseMessage(511));
			}
		}
		// 현재시간 이후에 진행 될 예정의 이벤트가 없으면 "이벤트 종료"
		return new DefaultResponse(512, getResponseMessage(512));
	}

	private DefaultResponse getEventInfo(HotdealsEvent event) {
		log.debug(event.toJsonLog());
		LocalDateTime nowDateTime = LocalDateTime.now();
		Duration from = Duration.between(nowDateTime, event.getDateFrom());
		Duration to = Duration.between(event.getDateTo(), nowDateTime);
		log.debug(">>> {}, {}", from.getNano(), to.getNano());
		if (from.getNano() > 0 && to.getNano() > 0) {
			Hotdeals hotdeals = new Hotdeals();
			hotdeals.setEventId(event.getEventId());
			hotdeals.setEventType(String.valueOf(event.getEventType()));
			hotdeals.setClose(false);
			sHotdeals = hotdeals;
			return new DefaultResponse(hotdeals);
		}
		return null;
	}

	/**
	 * Hotdeal Event에 등록한다.
	 *
	 * @param eventType the event type
	 * @param params the hotdeal request
	 * @return the default response
	 */
	public DefaultResponse setEventInfo(int eventType, HotdealRequest params) {
		if (sHotdeals == null) {
			DefaultResponse res = getEventInfo();
			if (res.getCode() >= 300) {
				return res;
			} else if (res.getResultData() == null) {
				return new DefaultResponse(511, getResponseMessage(511));
			}
			sHotdeals = (Hotdeals) res.getResultData();
		} else if (!duplicateCheck(sHotdeals.getEventId(), params.getPhoneNo(), params.getName())) {
			return new DefaultResponse(513, getResponseMessage(513));
		}
		if (NumberUtils.toInt(sHotdeals.getEventType(), 2) == 3) {
			// 선착순 / 응모형 이벤트는 Coupon 서버에 등록을 요청한다.
			log.debug("선착순 / 응모형 이벤트는 Coupon 서버에 등록 요청.");
		}

		HotdealsPick pick = new HotdealsPick();
		pick.setKey(new HotdealsPickKey(params.getPhoneNo(), sHotdeals.getEventId()));
		pick.setName(params.getName());
		pick.setAgreement(params.isAggrement());
		pick.setTimestamp(params.getTimestamp());
		hotdealsPickRepository.save(pick);

		Hotdeals event = new Hotdeals();
		event.setEventId(sHotdeals.getEventId());
		event.setEventType(sHotdeals.getEventType());
		event.setDuplicate(false);
		event.setClose(fcfsClosed);
		return new DefaultResponse(event);
	}

}
