package com.jk.spring.strategy;

import org.springframework.stereotype.Service;

/**
 * 전략 A
 */
@Service
public class ContextMethodA implements IContextMethod {

    @Override
    public void contextMethod (String context, String startegy) {
        context += " this is Strategy A.";
        System.out.println(context);
    }

}
