package com.jk.spring.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControllerTestObj {
	private String someId;
	private String someContent;

	private GOntrollerTestObjEmb gOntrollerTestObjEmb;
}
