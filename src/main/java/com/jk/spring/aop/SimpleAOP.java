package com.jk.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SimpleAOP {

    @Around(value = "@annotation(CustomAspect)")
    public Object printAOP(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Custom AOP - start");
        Object result = pjp.proceed();
        System.out.println("Custom AOP - end");

        return result;
    }

    @Before("execution(* com.jk.spring.service.UseService.*(..))")
    public void printTest() throws Throwable {
        System.out.println("Before Use Service");
    }
}
