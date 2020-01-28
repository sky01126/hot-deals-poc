package com.kt.service;

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
import com.kthcorp.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotdealService extends AbstractService {

	@Autowired
	private HotdealDao hotdealDao;

	@Autowired
	private HotdealsEventRepository hotdealsEventRepository;

	/**
	 * 이벤트 기본 정보 조회.
	 */
	public DefaultResponse getEventInfo() {
		List<HotdealsEvent> list = hotdealsEventRepository.findAll();

		// Sort.by("date_from").descending()
		if (!list.isEmpty()) {
			for (HotdealsEvent event : list) {
				log.debug(event.toJsonLog());
				Hotdeals hotdeals = new Hotdeals();
				hotdeals.setEventId(event.getEventId());
				hotdeals.setEventType(String.valueOf(event.getEventType()));
				hotdeals.setClose(false);
				return new DefaultResponse(hotdeals);
			}
		}
		return new DefaultResponse(511, getResponseMessage(511));
	}

	/**
	 * Hotdeal Event에 등록된 정보 조회
	 *
	 * @param eventId the event id
	 * @param phoneNo the phone number
	 * @return the default response
	 */
	public DefaultResponse getHotdealEvent(String eventId, String phoneNo) {
		String name = hotdealDao.getEventFcfs(eventId, phoneNo);
		if (StringUtils.isNotBlank(name)) {
			Hotdeals event = new Hotdeals();
			event.setEventId(eventId);
			event.setPhoneNo(phoneNo);
			event.setName(name);
			return new DefaultResponse(event);
		}
		return new DefaultResponse(204, "이벤트에 등록되지 않았습니다.");
	}

	/**
	 * Hotdeal Event에 등록한다.
	 *
	 * @param eventType the event type
	 * @param params the hotdeal request
	 * @return the default response
	 */
	public DefaultResponse setHotdealEvent(Integer eventType, HotdealRequest params) {
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
