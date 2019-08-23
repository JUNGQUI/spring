package com.jk.spring.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ContextMethod {

    private final ContextStrategy contextStrategy;

    @Autowired
    public ContextMethod(ContextStrategy contextStrategy) {
        this.contextStrategy = contextStrategy;
    }

    // 선택된 전략을 통해 입력받은 String 과 결합 후 출력, 받고 선택 후 출력하는 고정적인 context method
    public void contextMethod (String context, String startegy) {

        if (StringUtils.hasText(startegy)) {
            switch (startegy) {
                case "A":
                    context = contextStrategy.changeToA(context);
                    break;
                case "B":
                    context = contextStrategy.changeToB(context);
                    break;
                default:
                    context = "Nothing chosen";
                    break;
            }
        } else {
            context = "No strategy";
        }

        System.out.println(context);
    }

}
