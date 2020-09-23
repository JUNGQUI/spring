package com.jk.spring;

import com.jk.spring.builder.TestObject;
import com.jk.spring.builder.TestObjectRepository;
import com.jk.spring.builder.TestObjectSpecs;
import com.jk.spring.service.UseService;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    private final static FastDateFormat FDF = FastDateFormat.getInstance("yyyyMMdd");

    private String globalTest = "";

    @Autowired
    private TestObjectRepository testObjectRepository;

    @Autowired
    private UseService useService;

    private void regex(String something) {
        System.out.println(something.matches("[-]?[0-9]*")); //음수 양수 숫자만
    }

    @Test
    public void extendAndImplements() {
        useService.useInterface();
    }

    @Test
    public void contextLoads() {
//        useService.useStartegy();
        useService.useOverLoadingAndRiding("J Tag?");
//        useService.userIOC_DI();

        String test = "";

        setSomething(test); // 당연히 안됨
        setSomething(globalTest); // 역시 안됨

        System.out.println(test); // ""
        System.out.println(globalTest); // ""

        setSomething(TestObject.builder().build()); // TestObject 가 정상적으로 변경되었으나 휘발성으로 만들었기에 접근 불가능, code 종료 후 날아감
        TestObject newTestObject = TestObject.builder().build(); // 새 obj 로 선언
        setSomething(newTestObject); // 마찬가지로 정상적으로 변경, newTestObject 로 접근 가능
    }

    // side effect test
    private void setSomething(String something) {
        something = "nothing";
    }
    private void setSomething(TestObject testObject) {
        testObject.changeContent("side effect", "set 으로 object를 바꾼 content");
    }

    @Test
    public void builderPattern() {
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

    @Test
    public void useSpec() {
        // spec 으로 이와 같은 query 를
        List<TestObject> testObject_byName = testObjectRepository.findByName("JK-Test");
        // 이렇게 바꿀 수 있다.
        List<TestObject> testObjects_specByName = testObjectRepository.findAll(TestObjectSpecs.withName("JK-Test"));

        // like 검색 시 %%붙여야 하는거 실화냐
        // jpa repository 기본 설정이 저런가 봄
        List<TestObject> testObjects_byContent = testObjectRepository.findByContentLike("%데이터가%");

        Map<TestObjectSpecs.SearchKey, String> condition = new HashMap<>();
        condition.put(TestObjectSpecs.SearchKey.CONTENT, "데이터가");
        List<TestObject> testObjects_specByContent = testObjectRepository.findAll(TestObjectSpecs.withSearchKey(condition));
    }

    @Test
    public void simpleJavaTest() {
        TestObject test = TestObject.builder()
                .name("이름")
                .content("컨텐츠")
                .createdDate(new Date())
                .build();

        List<TestObject> testObjects = new ArrayList<>();

        testObjects.add(test);

        test.changeContent("이름2", "컨텐츠2");
        int result = 0;
        System.out.println("J Tag");
    }

    @Test
    public void typeSafeTest() {
//        System.out.println(1+"1");
//        System.out.println("It Must Print 4 time " * 4);
    }
}
