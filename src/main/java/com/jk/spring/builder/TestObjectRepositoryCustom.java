package com.jk.spring.builder;

public interface TestObjectRepositoryCustom {
	void findByNameNoCache(String name);
	void findByNameCache(String name);
	void refresh(String name);
}
