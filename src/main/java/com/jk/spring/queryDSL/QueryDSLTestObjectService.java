package com.jk.spring.queryDSL;

import com.jk.spring.builder.QTestObject;
import com.jk.spring.builder.TestObject;
import com.jk.spring.builder.TestObjectRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryDSLTestObjectService {

	private final EntityManager entityManager;
	private final TestObjectRepository testObjectRepository;

	@Transactional
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

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public TestObject testTransaction(Long id, String name, String content) {
		TestObject testObject = testObjectRepository.findById(id).orElse(null);

		if (testObject != null) {
			System.out.println(testObject.getId() + "/"
					+ testObject.getName() + "/"
					+ testObject.getContent());
		}

		testObject.changeContent(name, content);
		slowQuery();
		return testObject;
	}

	@Transactional
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

	private void slowQuery() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
