package com.jk.spring.mock;

public class JKMock {

    public int mockInteger;

    public JKMock(int mockInteger) {
        this.mockInteger = mockInteger;
    }

    public void mockTest(int testInteger) {
        if (mockInteger < testInteger) {
            throw new RuntimeException("숫자 넘지 마라");
        }
    }
}
