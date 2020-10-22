package com.jk.spring.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JKAnnotation
public class AnnotationObject {
	private String name;
	private String content;
}
