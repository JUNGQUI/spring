package com.jk.spring.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JKAnnotation(name = "not default")
public class AnnotationObjectNotDefault {
	private String name;
	private String content;
}
