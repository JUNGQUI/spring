package com.jk.spring;

import com.jk.spring.builder.TestObject;
import com.jk.spring.builder.TestObjectRepository;
import com.jk.spring.queryDSL.QueryDSLTestObjectService;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QueryDSLTest {
	@Autowired private QueryDSLTestObjectService queryDSLTestObjectService;
	@Autowired private TestObjectRepository testObjectRepository;

	@Test
	public void queryDSLTest() throws IllegalAccessException {
//		queryDSLTestObjectService.saveOrUpdate();
//
//		List<TestObject> testObjectsJpaRepository = testObjectRepository.findAll();
//		List<TestObject> testObjectsQueryDSL = queryDSLTestObjectService.queryDSLFetchMethod();

		TestObject.class.getTypeName();
		TestObject.class.getAnnotations();

		TestObject testObject = TestObject.builder()
				.id(1L)
				.name("name")
				.content("content")
				.createdDate(new Date())
				.updatedDate(null)
				.build();

		for (Field field : testObject.getClass().getDeclaredFields()) {
			field.getAnnotatedType();
			field.getType();
			field.getDeclaredAnnotations();
		}

		System.out.println("J Tag");
	}
}
