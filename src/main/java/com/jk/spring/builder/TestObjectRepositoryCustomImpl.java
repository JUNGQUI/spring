package com.jk.spring.builder;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class TestObjectRepositoryCustomImpl implements TestObjectRepositoryCustom {

	@Override
	public void findByNameNoCache(String name) {
		slowQuery();
	}

	@Override
	@Cacheable(value = "sampleCache1", key="#name")
	public void findByNameCache(String name) {
		slowQuery();
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
