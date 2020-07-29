package com.jk.spring;

import com.jk.spring.builder.TestObject;
import com.jk.spring.builder.TestObjectRepository;
import com.jk.spring.service.UseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private TestObjectRepository testObjectRepository;

    @Autowired
    private UseService useService;

    @Test
    public void contextLoads() {
//        useService.useInterface();
//        useService.useStartegy();
        useService.useOverLoadingAndRiding("J Tag?");
//        useService.userIOC_DI();

        TestObject saved = TestObject.builder()
                .name("JK_Test")
                .content("이야 이거 하나 만드는데 ㅈ빠지게 오래 걸렸다.")
                .createdDate(new Date())
                .build();

        testObjectRepository.saveAndFlush(saved);
        List<TestObject> testObjects = testObjectRepository.findAll();

        // 잘못된 수정 방식
//        TestObject updated = TestObject.builder()
//                .id(testObjects.get(0).getId())
//                .name("JK-Test")
//                .content("자 데이터가 변경되었다.")
//                .build();
//
//        testObjectRepository.saveAndFlush(updated);

        // DDD 와 builder pattern 의 올바른 수정 방식
        testObjects.get(0).changeContent("JK-Test", "자 데이터가 변경되었다.");
        testObjectRepository.saveAndFlush(testObjects.get(0));
    }

}
