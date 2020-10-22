package com.jk.spring;

import com.jk.spring.annotation.AnnotationObject;
import com.jk.spring.annotation.AnnotationObjectNotDefault;
import com.jk.spring.annotation.JKAnnotation;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnnotationTest {

	@Test
	public void annotationTest() {
		AnnotationObject annotationObject = new AnnotationObject(
				"name", "content"
		);
		
		AnnotationObjectNotDefault annotationObjectNotDefault = new AnnotationObjectNotDefault(
				"name not default",
				"content not default"
		);

		List<Annotation> annotations = new ArrayList<>(Arrays.asList(annotationObject.getClass().getAnnotations()));
		annotations.addAll(Arrays.asList(annotationObjectNotDefault.getClass().getAnnotations()));

		for(Annotation annotation : annotations){
			if(annotation instanceof JKAnnotation) {
				JKAnnotation myAnnotation = (JKAnnotation) annotation;
				System.out.println("name: " + myAnnotation.name());
			}
		}

		System.out.println("J Tag");
	}
}
