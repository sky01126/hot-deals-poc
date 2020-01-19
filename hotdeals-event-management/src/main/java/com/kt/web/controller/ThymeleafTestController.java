package com.kt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafTestController {

	@GetMapping("/thymeleaf/test")
	public String getUser(Model model) {
		model.addAttribute("test", "TEST");
		return "test";
	}

}
