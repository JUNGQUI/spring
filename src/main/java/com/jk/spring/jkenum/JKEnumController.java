package com.jk.spring.jkenum;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JKEnumController {
	@GetMapping("/jkEnumTest")
	public void jkEnumTest(
			@RequestParam("id") String id,
			@RequestParam("jkEnum") JKEnum jkEnum) {
		System.out.println("- JK Test\nID : " + id + "\nEnum : " + jkEnum);
	}
}
