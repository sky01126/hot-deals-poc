package com.kt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.commons.dto.request.HotdealRequest;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.persistence.model.HotdealEvent;
import com.kt.commons.service.AbstractService;
import com.kt.persistence.dao.HotdealDao;
import com.kthcorp.commons.lang.BooleanUtils;
import com.kthcorp.commons.lang.StringUtils;

@Service
@SuppressWarnings("all")
@Transactional(readOnly = true)
public class HotdealService extends AbstractService {

	@Autowired
	private HotdealDao hotdealDao;

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
			HotdealEvent event = new HotdealEvent();
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
		HotdealEvent event = new HotdealEvent();

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
