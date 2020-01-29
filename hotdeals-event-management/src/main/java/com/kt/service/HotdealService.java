package com.kt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.commons.dto.request.HotdealRequest;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.persistence.model.Hotdeals;
import com.kt.commons.service.AbstractService;
import com.kt.persistence.model.HotdealsEvent;
import com.kt.persistence.repositories.HotdealDao;
import com.kt.persistence.repositories.HotdealsEventRepository;
import com.kthcorp.commons.lang.BooleanUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotdealService extends AbstractService {

	public static Hotdeals sHotdeals = null;

	@Autowired
	private HotdealDao hotdealDao;

	@Autowired
	private HotdealsEventRepository hotdealsEventRepository;

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
		if (event.getDateFrom().isEqual(nowDateTime)
				|| (event.getDateFrom().isAfter(nowDateTime) && event.getDateTo().isBefore(nowDateTime))) {
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
	public DefaultResponse setHotdealEvent(int eventType, HotdealRequest params) {
		if (sHotdeals == null) {
			return new DefaultResponse(512, getResponseMessage(512));
		}

		if (duplicateCheck(sHotdeals.getEventId(), params.getPhoneNo(), params.getName())) {
			return new DefaultResponse(513, getResponseMessage(513));
		}

		Hotdeals event = new Hotdeals();

		boolean isCreate = hotdealDao.putEventFcfs(params);
		event.setDuplicate(BooleanUtils.isFalse(isCreate));

		if (isCreate) { // 신규 등록
			event.setEventId(params.getEventId());
			event.setPhoneNo(params.getPhoneNo());
			event.setName(params.getName());
			event.setAggrement(params.isAggrement());
			event.setTimestamp(params.getTimestamp());
		}
		return new DefaultResponse(event);
	}

}
