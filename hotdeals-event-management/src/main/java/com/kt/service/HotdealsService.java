package com.kt.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import com.kt.commons.persistence.model.HotdealsCoupon;
import com.kt.commons.persistence.model.HotdealsEvent;
import com.kt.commons.persistence.model.HotdealsPick;
import com.kt.commons.service.AbstractService;
import com.kt.kafka.HotdealConsumer;
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

	@Value("${coupon.server.url}")
	private String couponServerUrl;

	/**
	 * 이벤트 기본 정보 조회.
	 *
	 * @throws IOException
	 */
	public DefaultResponse getEventInfo() throws IOException {
		Hotdeals hotdeals = getCache();
		if (hotdeals == null) {
			String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			Set<String> keys = clusterStringRedisTemplate.keys(prefix + "*");
			if (keys.isEmpty()) {
				return new DefaultResponse(511, getResponseMessage(511));
			} else {
				List<String> list = new ArrayList<>(keys);
				Collections.sort(list, Collections.reverseOrder());
				String key = list.get(0);
				String value = clusterRedisValueOperations.get(key);
				HotdealsEvent hotdealsEvent = (HotdealsEvent) jsonToObject(value, HotdealsEvent.class);

				hotdeals = new Hotdeals();
				hotdeals.setEventId(hotdealsEvent.getEventId());
				hotdeals.setDateFrom(hotdealsEvent.getDateFrom());
				hotdeals.setDateTo(hotdealsEvent.getDateTo());

				HotdealsCoupon hotdealsCoupon = HotdealConsumer.hotdealsCoupon;
				if (hotdealsCoupon != null && StringUtils.equals(hotdeals.getEventId(), hotdealsCoupon.getEventId())) {
					hotdeals.setClose(hotdealsCoupon.isClosed());
				}
			}
		}
		log.debug(hotdeals.toJsonPrettify());

		// 시간 확인
		DateTime nowDateTime = DateTime.now();
		log.debug(">>> {}, {}", hotdeals.getDateFrom().isEqual(nowDateTime), hotdeals.getDateTo().isEqual(nowDateTime));
		log.debug(">>> {}, {}", hotdeals.getDateFrom().isBefore(nowDateTime),
				hotdeals.getDateTo().isAfter(nowDateTime));
		if (hotdeals.getDateFrom().isEqual(nowDateTime) || hotdeals.getDateTo().isEqual(nowDateTime)
				|| (hotdeals.getDateFrom().isBefore(nowDateTime) && hotdeals.getDateTo().isAfter(nowDateTime))) {
			setCache(hotdeals); // Redis Cache...
			return new DefaultResponse(hotdeals);
		} else {
			// 현재시간 이후에 진행 될 예정의 이벤트가 있으면 "이벤트 준비중"
			if (hotdeals != null && hotdeals.getDateFrom().isAfter(DateTime.now())) {
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
			return new DefaultResponse(511, getResponseMessage(511));
			// } else if (StringUtils.notEquals(params.getEventId(), hotdeals.getEventId())) {
			// // 클라이언트가 보내준 이벤트ID와 Cache된 이벤트ID가 상이한 경우 에러 발생
			// return new DefaultResponse(412, getResponseMessage(412));
		} else if (!duplicateCheck(hotdeals.getEventId(), params.getPhoneNo(), params.getName())) {
			return new DefaultResponse(513, getResponseMessage(513));
		}

		// 쿠폰아이디를 Parameter에 추가한다.
		params.setEventId(hotdeals.getEventId());

		// 선착순 / 응모형 이벤트는 Coupon 서버에 등록을 요청한다.
		if (HotdealConsumer.hotdealsCoupon != null) {
			log.debug("{} : {}", hotdeals.getEventId(), HotdealConsumer.hotdealsCoupon.getEventId());
		}
		// 선착순 / 응모형 이벤트는 Coupon 서버에 등록을 요청한다.
		if (NumberUtils.toInt(hotdeals.getEventType(), 2) == 3 //
				&& (HotdealConsumer.hotdealsCoupon == null
						|| StringUtils.notEquals(hotdeals.getEventId(), HotdealConsumer.hotdealsCoupon.getEventId())
						|| !HotdealConsumer.hotdealsCoupon.isClosed())) {
			log.info("선착순 / 응모형 이벤트는 Coupon 서버에 등록 요청.");
			threadPoolTaskExecutor.execute(new CouponThread(couponServerUrl, params));
		}

		HotdealsPick pick = new HotdealsPick();
		pick.setPhoneNo(params.getPhoneNo());
		pick.setEventId(hotdeals.getEventId());
		pick.setName(params.getName());
		pick.setAgreement(params.isAggrement());
		pick.setTimestamp(params.getTimestamp());
		clusterRedisValueOperations.set(getRedisPickKey(pick.getEventId(), pick.getPhoneNo()), pick.toJson());
		log.debug(">>> {}", clusterRedisValueOperations.get(getRedisPickKey(pick.getEventId(), pick.getPhoneNo())));

		HotdealsCoupon hotdealsCoupon = HotdealConsumer.hotdealsCoupon;
		if (hotdealsCoupon != null && StringUtils.equals(hotdeals.getEventId(), hotdealsCoupon.getEventId())) {
			hotdeals.setClose(hotdealsCoupon.isClosed());
		} else if (NumberUtils.toInt(hotdeals.getEventType(), 2) == 3) {
			hotdeals.setClose(false);
		}
		hotdeals.setDateFrom(null);
		hotdeals.setDateTo(null);
		return new DefaultResponse(hotdeals);
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
