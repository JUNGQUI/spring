package com.jk.spring.controller;

import com.jk.spring.builder.TestObject;
import com.jk.spring.jkenum.JKEnum;
import com.jk.spring.queryDSL.QueryDSLTestObjectService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

	private final QueryDSLTestObjectService queryDSLTestObjectService;

	@GetMapping(value = "/test")
	public void test(@RequestBody ControllerTestObj controllerTestObj) {
		System.out.println("J Tag");
	}

	@GetMapping(value = "/test/all")
	public List<TestObject> all() {
		return queryDSLTestObjectService.listAll();
	}

	@GetMapping(value = "/test/init")
	public void init() {
		queryDSLTestObjectService.saveOrUpdate();
	}

	@GetMapping(value = "/test/initWithEnum")
	public void initWithEnum(@RequestParam (value = "jkEnum") JKEnum jkEnum) {
		queryDSLTestObjectService.saveWithEnum(jkEnum);
	}
	
	@GetMapping(value = "/test/transaction")
	public void testTransaction(
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "content") String content) {
		long startTime = System.currentTimeMillis();
		LocalDateTime startDate = LocalDateTime.now();
		queryDSLTestObjectService.testTransaction(id, name, content);
		System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(startDate + " / " + LocalDateTime.now());
	}
}
