package com.jk.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SimpleAOP {

    @Around("@annotation(CustomAspect)")
    public Object printAOP(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Custom AOP - start");
        Object result = pjp.proceed();
        System.out.println("Custom AOP - end");

        return result;
    }
}
