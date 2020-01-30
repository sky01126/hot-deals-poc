package com.kt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.commons.dto.request.HotdealsRequest;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.web.controller.AbstractController;
import com.kt.commons.web.util.ResponseUtils;
import com.kt.dto.request.HotdealsEventRequest;
import com.kt.service.HotdealsService;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RequestMapping(path = { "api/v1" }, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@SuppressWarnings("all")
public class HotdealsCouponController extends AbstractController {

	@Autowired
	private HotdealsService hotdealsService;

	/**
	 * 신규 이벤트 추가.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @return Response DTO(Data Transfer Object)
	 */
	@PostMapping(path = "event")
	public ResponseEntity<Object> postEventInfo(HttpServletRequest request, HttpServletResponse response,
			@Valid HotdealsEventRequest params, BindingResult result) {
		// 필수 파라미터가 없는 경우의 에러 처리.
		checkForErrors(result);
		log.debug(params.toJson());
		String eventId = hotdealsService.setEventInfo(params);
		DefaultResponse res = new DefaultResponse();
		res.putResultMap("event_id", eventId);
		return ResponseUtils.resultJson(request, res);
	}

	/**
	 * 신규 이벤트 추가.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @return Response DTO(Data Transfer Object)
	 */
	@ApiIgnore
	@PostMapping(path = "event/coupon")
	public ResponseEntity<Object> postEventCoupon(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody HotdealsRequest params, BindingResult result) {
		// 필수 파라미터가 없는 경우의 에러 처리.
		checkForErrors(result);
		log.debug(params.toJson());
		// hotdealsService.setEventFcfsInfo(params);
		return ResponseUtils.resultJson(request);
	}

}
