package com.jk.spring.strategy;

public interface IContextMethod {
    // 선택된 전략을 통해 입력받은 String 과 결합 후 출력, 받고 선택 후 출력하는 고정적인 context method
    void contextMethod(String context, String startegy);
}
