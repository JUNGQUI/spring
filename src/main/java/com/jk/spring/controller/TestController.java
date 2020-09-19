package com.jk.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping(value = "/test")
	public void test(@RequestBody ControllerTestObj controllerTestObj) {
		System.out.println("J Tag");
	}
}
