package com.kt.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Comparator;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.kt.commons.dto.request.HotdealsRequest;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.persistence.model.Hotdeals;
import com.kt.commons.persistence.model.HotdealsEvent;
import com.kt.commons.persistence.model.HotdealsPick;
import com.kt.commons.persistence.model.HotdealsPickKey;
import com.kt.commons.persistence.repositories.HotdealsEventRepository;
import com.kt.commons.persistence.repositories.HotdealsPickRepository;
import com.kt.commons.service.AbstractService;
import com.kthcorp.commons.lang.JsonUtils;
import com.kthcorp.commons.lang.NumberUtils;
import com.kthcorp.commons.lang.StringUtils;
import com.kthcorp.commons.lang.http.HttpClientManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotdealsService extends AbstractService {

	/**
	 * Thread Pool Task Executor
	 */
	@Autowired
	protected ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private HotdealsEventRepository hotdealsEventRepository;

	@Autowired
	private HotdealsPickRepository hotdealsPickRepository;

	@Value("${coupon.server.url}")
	private String couponServerUrl;

	/**
	 * 이벤트 기본 정보 조회.
	 */
	public DefaultResponse getEventInfo() {
		List<HotdealsEvent> list = hotdealsEventRepository.findAll();
		if (!list.isEmpty()) {
			// 리스트를 Descending으로 정렬한다.
			list.sort(new HotdealsEventSorter());

			HotdealsEvent event = null;
			for (int i = 0; i < list.size(); i++) {
				event = list.get(i);
				Hotdeals hotdeals = getEventInfo(event);
				if (hotdeals != null) {
					return new DefaultResponse(hotdeals);
				}
			}

			DateTime.now().isAfter(DateTime.now());
			// 현재시간 이후에 진행 될 예정의 이벤트가 있으면 "이벤트 준비중"
			if (event != null && event.getDateFrom().isAfter(DateTime.now())) {
				return new DefaultResponse(511, getResponseMessage(511));
			}
		}
		// 현재시간 이후에 진행 될 예정의 이벤트가 없으면 "이벤트 종료"
		return new DefaultResponse(512, getResponseMessage(512));
	}

	/**
	 * Hotdeal Event에 등록한다.
	 *
	 * @param eventType the event type
	 * @param params the hotdeal request
	 * @return the default response
	 */
	public DefaultResponse setEventInfo(int eventType, HotdealsRequest params) {
		Hotdeals hotdeals = getCache();
		if (hotdeals != null) {
			log.debug(hotdeals.toJsonPrettify());
		}
		if (hotdeals == null) {
			DefaultResponse res = getEventInfo();
			if (res.getCode() >= 300) {
				return res;
			} else if (res.getResultData() == null) {
				return new DefaultResponse(511, getResponseMessage(511));
			}
			hotdeals = (Hotdeals) res.getResultData();
		} else if (StringUtils.notEquals(params.getEventId(), hotdeals.getEventId())) {
			// 클라이언트가 보내준 이벤트ID와 Cache된 이벤트ID가 상이한 경우 에러 발생
			return new DefaultResponse(412, getResponseMessage(412));
		} else if (!duplicateCheck(hotdeals.getEventId(), params.getPhoneNo(), params.getName())) {
			return new DefaultResponse(513, getResponseMessage(513));
		}

		// 선착순 / 응모형 이벤트는 Coupon 서버에 등록을 요청한다.
		if (NumberUtils.toInt(hotdeals.getEventType(), 2) == 3) {
			log.debug("선착순 / 응모형 이벤트는 Coupon 서버에 등록 요청.");
			threadPoolTaskExecutor.execute(new CouponThread(couponServerUrl, params));
		}

		HotdealsPick pick = new HotdealsPick();
		pick.setKey(new HotdealsPickKey(params.getPhoneNo(), hotdeals.getEventId()));
		pick.setName(params.getName());
		pick.setAgreement(params.isAggrement());
		pick.setTimestamp(params.getTimestamp());
		hotdealsPickRepository.save(pick);

		Hotdeals event = new Hotdeals();
		event.setEventId(hotdeals.getEventId());
		event.setEventType(hotdeals.getEventType());
		event.setDuplicate(false);
		event.setClose(fcfsClosed);
		return new DefaultResponse(event);
	}

	public static class HotdealsEventSorter implements Comparator<HotdealsEvent> {

		@Override
		public int compare(HotdealsEvent o1, HotdealsEvent o2) {
			return o2.getKey().getEventId().compareTo(o1.getKey().getEventId());
		}

	}

	protected class CouponThread implements Runnable {

		private String url;

		private HotdealsRequest request;

		public CouponThread(String url, HotdealsRequest params) {
			this.url = url;
			this.request = params;
		}

		@Override
		public void run() {
			CloseableHttpResponse httpResponse = null;
			HttpClientManager httpManager = null;
			try {
				log.debug(JsonUtils.getJsonLogIndent(request));
				httpManager = HttpClientManager.post();
				httpManager.setConnectTimeout(2 * 1000);
				httpManager.setSocketTimeout(5 * 1000);
				httpManager.setAccept("application/json");
				httpManager.setContentType("application/json");
				httpManager.setData(request.toJson());

				httpResponse = httpManager.execute(url);
				log.debug(EntityUtils.toString(httpResponse.getEntity()));
			} catch (GeneralSecurityException | IOException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}

}
