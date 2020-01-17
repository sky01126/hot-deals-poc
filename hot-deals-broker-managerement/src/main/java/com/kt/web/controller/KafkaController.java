package com.kt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.commons.config.Constants;
import com.kt.commons.dto.request.HotdealRequest;
import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.persistence.model.HotdealEvent;
import com.kt.commons.web.controller.AbstractController;
import com.kt.commons.web.util.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(path = { "kafka" }, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@SuppressWarnings("all")
public class KafkaController extends AbstractController {

	@Autowired
	private KafkaTemplate<Object, Object> template;

	/**
	 * 선착순
	 * 테스트 URL : http://localhost:8080/kafka/fcfs/pub?event_id=2020011401&phone_num=01012345678&name=홍길동
	 */
	@GetMapping(path = "fcfs/pub")
	public ResponseEntity<Object> fcfsPub(HttpServletRequest request, HttpServletResponse response,
			@Valid HotdealRequest params, BindingResult result) {
		// 필수 파라미터가 없는 경우의 에러 처리.
		checkForErrors(result);
		log.debug(params.toJson());

		HotdealEvent event = new HotdealEvent();
		event.setEventId(params.getEventId());
		event.setPhoneNo(params.getPhoneNo());
		event.setName(params.getName());
		this.template.send(Constants.KAFKA_TOPIC_HOTDEAL_FCFS, event);
		return ResponseUtils.resultJson(request, new DefaultResponse());
	}

	/**
	 * 선착순
	 * 테스트 URL : http://localhost:8080/kafka/pick/pub?event_id=2020011401&phone_num=01012345678&name=홍길동
	 */
	@GetMapping(path = "pick/pub")
	public ResponseEntity<Object> pickPub(HttpServletRequest request, HttpServletResponse response,
			@Valid HotdealRequest params, BindingResult result) {
		// 필수 파라미터가 없는 경우의 에러 처리.
		checkForErrors(result);
		log.debug(params.toJson());

		HotdealEvent event = new HotdealEvent();
		event.setEventId(params.getEventId());
		event.setPhoneNo(params.getPhoneNo());
		event.setName(params.getName());
		event.setAggrement(params.isAggrement());
		event.setTimestamp(params.getTimestamp());
		this.template.send(Constants.KAFKA_TOPIC_HOTDEAL_PICK, event);
		return ResponseUtils.resultJson(request, new DefaultResponse());
	}

}
