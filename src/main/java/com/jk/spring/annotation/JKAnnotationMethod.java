package com.jk.spring.annotation;

import java.lang.reflect.Field;

public class JKAnnotationMethod {
	public static void jkAnnotationMethod(Class<?> annotatedField) {
		for (Field field : annotatedField.getDeclaredFields()) {
			JKAnnotation annotation = field.getAnnotation(JKAnnotation.class);

			if(annotation != null) {
				System.out.println(annotation.name());
			}
		}
	}
}
