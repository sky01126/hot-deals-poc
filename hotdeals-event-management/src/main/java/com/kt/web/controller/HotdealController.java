package com.kt.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.commons.dto.request.HotdealRequest;
import com.kt.commons.web.controller.AbstractController;
import com.kt.commons.web.util.ResponseUtils;
import com.kt.service.HotdealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(value = "HOT Deals 이벤트 신청 / 조회 테스트 API")
@Slf4j
@RequestMapping(path = { "api/v1" }, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class HotdealController extends AbstractController {

	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;

	@Autowired
	private HotdealService hotdealService;

	/**
	 * GET
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param eventId the event id parameter
	 * @param phoneNo the phone number parameter
	 * @return Response DTO(Data Transfer Object)
	 */
	@GetMapping(path = "event/id/{EVENT_ID}/phone_no/{PHONE_NO}")
	public ResponseEntity<Object> getEventInfo(HttpServletRequest request //
			, HttpServletResponse response //
			, @ApiParam(value = "이벤트 ID", defaultValue = "2020011301") @PathVariable(name = "EVENT_ID") String eventId //
			,
			@ApiParam(value = "핸드폰번호", defaultValue = "01012345678") @PathVariable(name = "PHONE_NO") String phoneNo) {
		log.debug("Event ID: {}, Phone Number: {}", eventId, phoneNo);
		return ResponseUtils.resultJson(request, hotdealService.getHotdealEvent(eventId, phoneNo));
	}

	/**
	 * Event 정보 수정 / 등록
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param eventType the event type request parameter (1:선착순, 2:응모형: 3:선착순+응모형)
	 * @param params the validation request parameter
	 * @param result the binding result
	 * @return Response DTO(Data Transfer Object)
	 */
	@PutMapping(path = "event/type/{EVENT_TYPE}")
	public ResponseEntity<Object> putEventInfo( //
			HttpServletRequest request //
			, HttpServletResponse response //
			,
			@ApiParam(value = "이벤트 TYPE (1:선착순, 2:응모형: 3:선착순+이벤트)", example = "3") @PathVariable(name = "EVENT_TYPE") Integer eventType //
			, @Valid @RequestBody HotdealRequest params //
			, BindingResult result) {
		// 필수 파라미터가 없는 경우의 에러 처리.
		checkForErrors(result);
		log.debug(params.toJson());

		return resultJson(request, hotdealService.setHotdealEvent(eventType, params));
	}

}
