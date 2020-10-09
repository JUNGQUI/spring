package com.jk.spring.ehcache;

import com.jk.spring.builder.TestObject;
import com.jk.spring.builder.TestObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableCaching
@RestController
@RequiredArgsConstructor
public class JKEhcache {

	private final TestObjectRepository testObjectRepository;

	@GetMapping(value = "/cache/{name}")
	public void ehcache(@PathVariable(value = "name") String name) {
		long startTime = System.currentTimeMillis();
		TestObject testObject = testObjectRepository.findByNameCache(name);
		long endTime = System.currentTimeMillis();
		System.out.println(name + " printed, " + (endTime - startTime));
	}

	@GetMapping(value = "/nocache/{name}")
	public void noEhcache(@PathVariable(value = "name") String name) {
		long startTime = System.currentTimeMillis();
		TestObject testObject = testObjectRepository.findByNameNoCache(name);
		long endTime = System.currentTimeMillis();
		System.out.println(name + " printed, " + (endTime - startTime));
	}

	@GetMapping(value = "/cache/refresh/{name}")
	public void refresh(@PathVariable(value = "name") String name) {
		testObjectRepository.refresh(name);
	}
}
