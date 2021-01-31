package com.jk.spring.mock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JKMockService {

	private final JKMockRepository jkMockRepository;

	public int returnNumber() {
		return jkMockRepository.returnNumber();
	}

	public String returnString(String value) {
		return jkMockRepository.returnString(value);
	}
}
