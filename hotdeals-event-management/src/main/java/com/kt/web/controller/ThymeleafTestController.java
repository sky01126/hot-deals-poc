package com.kt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class ThymeleafTestController {

	@GetMapping({ "/", "/index", "/index.htm", "/index.html" })
	public String getIndex() {
		return "index";
	}

}
