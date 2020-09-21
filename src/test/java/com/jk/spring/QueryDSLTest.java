package com.jk.spring;

import com.jk.spring.builder.TestObject;
import com.jk.spring.builder.TestObjectRepository;
import com.jk.spring.queryDSL.QueryDSLTestObjectService;
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
	public void queryDSLTest() {
		queryDSLTestObjectService.saveOrUpdate();

		List<TestObject> testObjectsJpaRepository = testObjectRepository.findAll();
		List<TestObject> testObjectsQueryDSL = queryDSLTestObjectService.queryDSLFetchMethod();

		System.out.println("J Tag");
	}
}
