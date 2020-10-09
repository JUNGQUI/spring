package com.jk.spring.builder;

public interface TestObjectRepositoryCustom {
	TestObject findByNameNoCache(String name);
	TestObject findByNameCache(String name);
	void refresh(String name);
}
