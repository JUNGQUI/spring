package com.jk.spring.builder;

import java.util.Date;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class TestObjectRepositoryCustomImpl implements TestObjectRepositoryCustom {

	@Override
	public TestObject findByNameNoCache(String name) {
		slowQuery();
		return TestObject.builder()
				.name(name)
				.createdDate(new Date())
				.build();
	}

	@Override
	@Cacheable(value = "sampleCache1", key="#name")
	public TestObject findByNameCache(String name) {
		slowQuery();
		return TestObject.builder()
				.name(name)
				.createdDate(new Date())
				.build();
	}

	@Override
	@CacheEvict(value = "sampleCache1", key="#name")
	public void refresh(String name) {
		System.out.println("cache clear");
	}

	// 빅쿼리를 돌린다는 가정
	private void slowQuery() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
