package com.jk.spring.strategy;

import org.springframework.stereotype.Service;

@Service
public class ContextStrategy {
    // 받은 strategy 에 대해 가공 후 return

    // strategy A
    public String changeToA(String context) {
        return context += " - strategy A was used.";
    }

    // strategy B
    public String changeToB(String context) {
        return context += " - strategy B was used.";
    }
}
