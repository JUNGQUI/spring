package com.jk.spring.builder;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Date;
import javax.persistence.EntityManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class TestObjectRepositoryCustomImpl implements TestObjectRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public TestObjectRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

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

	public void queryDSL() {
		queryFactory.selectFrom(QTestObject.testObject)
				.join(QTestObject.testObject.testObjectForRelation, QTestObjectForRelation.testObjectForRelation)
				.fetchJoin()
				.fetch()
		.stream().map(testObject -> {
			System.out.println(testObject.getContent());
			return null;
		});
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
