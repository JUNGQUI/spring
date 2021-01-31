package com.jk.spring.mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Description;

// @Mock annotation 을 mocking class 로 활성화
@RunWith(MockitoJUnitRunner.class)
public class JKMockServiceTest {

	// service 에서 사용하는 repository mocking 화
	@Mock
	private JKMockRepository repository;

	// 실제 사용할 mocking service
	@Mock
	private JKMockService jkMockService;

	@Before
	public void setup() {
		// mocking 이기 때문에 제대로 된 데이터가 return 되지 않는다. 따라서 값을 지정해준다.
		when(jkMockService.returnNumber()).thenReturn(15);

		// 먼저 전체에 대해서는 이와 같이 return 하게 만들고
		when(jkMockService.returnString(any())).thenReturn("string");
		// 특정 환경에서는 특정 값을 출력하게 수정한다.
		when(jkMockService.returnString("jklee")).thenReturn("JK_Lee");
	}

	@Test
	@Description("Mocking class 와 Mocking Repository 정상, 숫자")
	public void returnIntMock() {
		Assertions.assertEquals(15, jkMockService.returnNumber());
	}

	@Test
	@Description("Mocking class 와 Mocking Repository 정상, 문자열")
	public void returnStringMock() {
		Assertions.assertEquals("JK_Lee", jkMockService.returnString("jklee"));
		Assertions.assertEquals("string", jkMockService.returnString("other String"));
	}

}