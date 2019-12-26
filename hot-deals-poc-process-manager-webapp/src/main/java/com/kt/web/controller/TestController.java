package com.kt.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.commons.dto.response.DefaultResponse;
import com.kt.commons.web.controller.AbstractController;
import com.kt.commons.web.util.ResponseUtils;
import com.kt.dto.request.TestRequest;
import com.kt.service.TestService;
import com.kthcorp.commons.lang.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(path = { "test" }, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@SuppressWarnings("all")
public class TestController extends AbstractController {

	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;

	@Resource(name = "stringRedisTemplate")
	private ListOperations<String, String> listOperations;

	@Autowired
	private TestService testService;

	/**
	 * GET Locale
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @return Response DTO(Data Transfer Object)
	 */
	@GetMapping(path = "locale")
	public ResponseEntity<Object> localeTest(HttpServletRequest request, HttpServletResponse response) {
		DefaultResponse res = new DefaultResponse(200, getResponseMessage(200));
		return ResponseUtils.resultJson(request, res);
	}

	@GetMapping(path = "redis")
	public ResponseEntity<Object> get(HttpServletRequest request, HttpServletResponse response) {
		double dValue = Math.random();
		String nowDate = DateUtils.toDateString("yyyyMMdd");
		String key = "test" + (int) (dValue * 10 + 1);
		System.out.println("KYE=" + key + ", VALUE=" + "test - " + dValue);

		boolean isInsert = hashOperations.putIfAbsent("HASH:" + nowDate, key, "test - " + dValue);
		Long count = listOperations.leftPush("PUSH:" + nowDate, "test - " + dValue);
		System.out.println("COUNT=" + count);

		Map<String, Object> result = new HashMap<>();
		result.put("result", 200);
		result.put("result_msg", "SUCCESS");
		result.put("count", count);
		result.put("is_insert", isInsert);
		result.put("key", key);
		result.put("value", hashOperations.get("HASH:" + nowDate, key));

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<>(result, headers, HttpStatus.OK);
	}

	/**
	 * GET
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param name1 the name1 parameter
	 * @param name2 the name2 parameter
	 * @return Response DTO(Data Transfer Object)
	 */
	// @GetMapping(path = "get")
	// public ResponseEntity<Object> doGet(HttpServletRequest request, HttpServletResponse response,
	// @RequestParam(value = "id") String id,
	// @RequestParam(value = "name", required = false) String name) {
	// if (log.isDebugEnabled()) {
	// log.debug("ID: {}, Name: {}", id, name);
	// }
	// return ResponseUtils.resultJson(request, testService.getTest());
	// }
	@GetMapping(path = "get")
	public ResponseEntity<Object> doGet(HttpServletRequest request, HttpServletResponse response,
			@Valid TestRequest params, BindingResult result) {
		if (log.isDebugEnabled()) {
			log.debug("{}", params.toJsonLog());
		}
		checkForErrors(result); // 필수 파라미터 체크.
		if (log.isDebugEnabled()) {
			log.debug("ID: {}, Name: {}", params.getId(), params.getName());
		}
		return ResponseUtils.resultJson(request, testService.getTest());
	}

	/**
	 * POST
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param params the validation request parameter
	 * @param result the binding result
	 * @return Response DTO(Data Transfer Object)
	 */
	@PostMapping(path = "post")
	public ResponseEntity<Object> doPost(HttpServletRequest request, HttpServletResponse response,
			@Valid TestRequest params, BindingResult result) {
		if (log.isDebugEnabled()) {
			log.debug("{}", params.toJsonLog());
		}
		checkForErrors(result); // 필수 파라미터 체크.
		return ResponseUtils.resultJson(request, testService.getTest());
	}

	/**
	 * PUT
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param params the validation request parameter
	 * @param result the binding result
	 * @return Response DTO(Data Transfer Object)
	 */
	@PutMapping(path = "put")
	public ResponseEntity<Object> doPut(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody TestRequest params, BindingResult result) {
		if (log.isDebugEnabled()) {
			log.debug("{}", params.toJsonLog());
		}
		checkForErrors(result); // 필수 파라미터 체크.
		return ResponseUtils.resultJson(request, testService.getTest());
	}

	/**
	 * DELETE
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param params the validation request parameter
	 * @param result the binding result
	 * @return Response DTO(Data Transfer Object)
	 */
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<Object> doDelete(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(name = "id") Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("ID={}", id);
		}
		return ResponseUtils.resultJson(request, testService.getTest());
	}

}
