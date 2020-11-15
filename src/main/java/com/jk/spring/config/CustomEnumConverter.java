package com.jk.spring.config;

import com.jk.spring.jkenum.JKEnum;
import org.springframework.core.convert.converter.Converter;

public class CustomEnumConverter implements Converter<String, JKEnum> {
	@Override
	public JKEnum convert(String source) {
		try {
			return JKEnum.valueOf(source);
		} catch(Exception e) {
			return null;
		}
	}
}
