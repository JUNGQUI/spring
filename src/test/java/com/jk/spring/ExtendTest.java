package com.jk.spring;

import com.jk.spring.extend.ExtendTestB;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExtendTest {

	@Test
	public void test() {
		ExtendTestB extendTestB = new ExtendTestB();

		extendTestB.bMethod();
		extendTestB.aMethod();
	}
}
