package com.jk.spring.strategy;

import org.springframework.stereotype.Service;

/**
 * 전략 B
 */
@Service
public class ContextMethodB implements IContextMethod {

    @Override
    public void contextMethod (String context, String startegy) {
        context += " this is Strategy B.";
        System.out.println(context);
    }
}
