package com.jk.spring;

import com.jk.spring.annotation.JKAnnotation;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnnotationTest {

	@JKAnnotation(name = "test1")
	public String jkAnnotation_1;

	@JKAnnotation
	public String jkAnnotation_2;

	@Test
	public void annotationTest() {
		jkAnnotation_1 = "test";
		jkAnnotation_2 = "test";

		System.out.println(
				jkAnnotation_1.getClass().getAnnotation(JKAnnotation.class).name()
		);

		System.out.println(
				jkAnnotation_2.getClass().getAnnotation(JKAnnotation.class).name()
		);

		System.out.println("J Tag");
	}
}
