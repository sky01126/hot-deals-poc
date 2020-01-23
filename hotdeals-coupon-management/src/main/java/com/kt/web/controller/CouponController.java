package com.kt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.web.controller.AbstractController;
import com.kt.commons.web.util.ResponseUtils;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(path = { "api/v1" }, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@SuppressWarnings("all")
public class CouponController extends AbstractController {

	/**
	 * GET Locale
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @return Response DTO(Data Transfer Object)
	 */
	@PutMapping(path = "event/id/{EVENT_ID}/fcfs/coupon/count/{COUNT}")
	public ResponseEntity<Object> localeTest(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "이벤트 ID", example = "2020011301") @PathVariable(name = "EVENT_ID") String eventId,
			@ApiParam(value = "선착순 이벤트 COUNT 설정", example = "100") @PathVariable(name = "COUNT") Integer count) {
		DefaultResponse res = new DefaultResponse(200, getResponseMessage(200));
		return ResponseUtils.resultJson(request, res);
	}
}
