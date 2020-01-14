package com.kt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(path = { "test" }, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@SuppressWarnings("all")
public class TestController extends AbstractController {

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
