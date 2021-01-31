package com.jk.spring.mock;

import org.springframework.stereotype.Service;

@Service
public class JKMockRepository {
	public int returnNumber() {
		return 0;
	}

	public String returnString(String value) {
		return "string";
	}
}
