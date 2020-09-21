package com.jk.spring.queryDSL;

import com.jk.spring.builder.QTestObject;
import com.jk.spring.builder.TestObject;
import com.jk.spring.builder.TestObjectRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class QueryDSLTestObjectService {

	private final EntityManager entityManager;
	private final TestObjectRepository testObjectRepository;

	public void saveOrUpdate() {
		testObjectRepository.saveAndFlush(
				TestObject.builder()
						.name("jk_1")
						.content("contenst 1")
						.createdDate(new Date())
						.build()
		);

		testObjectRepository.saveAndFlush(
				TestObject.builder()
						.name("jk_2")
						.content("contenst 2")
						.createdDate(new Date())
						.build()
		);
	}

	public List<TestObject> queryDSLFetchMethod() {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

		QTestObject testObject = QTestObject.testObject;

		return jpaQueryFactory
				.selectFrom(testObject)
				.where(
						testObject.name.like("%jk%")
				)
				.fetch();
	}
}
