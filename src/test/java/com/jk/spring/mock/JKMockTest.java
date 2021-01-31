package com.jk.spring.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Description;

@RunWith(MockitoJUnitRunner.class)
public class JKMockTest {

    private Integer lengthOfInteger;

    private LengthValidator lengthValidator;

    @Before
    public void setup () {
        lengthOfInteger = 6;
        lengthValidator = new LengthValidator(lengthOfInteger);
    }

    @Test
    @Description("길이 준수 파라미터")
    public void lengthExceptionNormal() {
        String name = "jklee";
        Assertions.assertDoesNotThrow(() -> lengthValidator.validator(name));
    }

    @Test
    @Description("길이 초과 파라미터")
    public void lengthExceptionAbnormal() {
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < lengthOfInteger; i++) {
            name.append("*");
        }

        name.append("jklee");

        Assertions.assertThrows(RuntimeException.class, () -> lengthValidator.validator(name.toString()));
    }

    @Test
    @Description("길이 준수 모킹 파라미터")
    public void mockingLengthExceptionNormal() {
        // TODO implement
    }

    @Test
    @Description("길이 초과 모킹 파라미터")
    public void mockingLengthExceptionAbnormal() {
        // TODO implement
    }
}