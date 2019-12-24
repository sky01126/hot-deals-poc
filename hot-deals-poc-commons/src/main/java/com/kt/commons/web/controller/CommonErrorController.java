/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kt.commons.web.util.ResponseUtils;
import com.kthcorp.commons.lang.StringUtils;

/**
 * CommonErrorController.java
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@Controller
@SuppressWarnings("all")
public class CommonErrorController {

	/**
	 * 공통 에러 페이지 설정.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param statusCode the http status code
	 * @param redirectUrl the redirect url
	 * @param timeout 페이지 이동 Timeout (sec)
	 * @return the model and view
	 */
	@GetMapping("/commons/error/{statusCode}")
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Integer statusCode, @RequestParam(name = "redirect-url", required = false) String redirectUrl,
			@RequestParam(required = false) Long timeout) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(ResponseUtils.URL_ATTRIBUTE_NAME, "/");
		if (timeout != null && timeout > 10) {
			mav.addObject("timeout", 3);
		} else if (timeout != null) {
			mav.addObject("timeout", timeout);
		}
		if (StringUtils.isNotBlank(redirectUrl)) {
			mav.addObject(ResponseUtils.URL_ATTRIBUTE_NAME, redirectUrl);
		}
		mav.setViewName("error/" + statusCode);
		return mav;
	}

}
