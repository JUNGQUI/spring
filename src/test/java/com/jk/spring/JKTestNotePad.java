package com.jk.spring;

import com.jk.spring.finalAndStatic.FinalAndStatic;
import com.jk.spring.finalAndStatic.JKStaticMethod;
import com.jk.spring.lambda.JKLambda;
import com.jk.spring.lock.JKLock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JKTestNotePad {

    @Test
    public void finalAndStaticTest() {
        FinalAndStatic finalAndStatic = new FinalAndStatic();

        finalAndStatic.staticTest();

//        FinalAndStatic.StaticInteger one = new FinalAndStatic.StaticInteger();
//
//        System.out.println("first : " + one.nonStaticInteger); // 1
//        one.getStaticInteger(); // 1
//
//        FinalAndStatic.StaticInteger two = new FinalAndStatic.StaticInteger();
//
//        System.out.println("second : " + two.nonStaticInteger); // 1
//        two.getStaticInteger(); // static 이 아닐 경우 1이 예상되나 실제로 static 으로 되어 이미 one 이 생성 될 때 ++ 되었기 때문에 사실상 2가 반환

        // 둘 다 사용 가능하다. 다만 static method 의 경우 별도의 instance 가 필요 없이 (new instance 를 의미) class 내에서 바로 호출 가능하다.
        // 그 이유는 이미 static 으로 memory 위에 올라가 있고 compile 이 끝난 상태이기 때문이다.
        JKStaticMethod jkStaticMethod = new JKStaticMethod();
        jkStaticMethod.staticMethod();
        JKStaticMethod.staticMethod();
    }

    @Test
    public void lambdaTest() {
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.forEach(
                // lambda expression
                System.out::println
        );

        JKLambda jkLambda1 = new JKLambda() {
            @Override
            public String stringConcat(String s1, String s2) {
                return s1 + " " + s2 + "JK lambda, no lambda";
            }
        };

        JKLambda jKlambda = (a, b) -> a + " " + b + " JK lambda, first";
        JKLambda jklambda2 = (a, b) -> b + " " + a + " JK lambda, second";

        String concatenatedString = jKlambda.stringConcat("first", "second");
        String concatenatedString2 = jklambda2.stringConcat("second", "first");

        System.out.println(concatenatedString);
        System.out.println(concatenatedString2);

        System.out.println("J Tag");
    }

    @Test
    public synchronized void threadTest() {
        try {
            JKLock jkLock = new JKLock();

            long startSync = System.currentTimeMillis();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    jkLock.checkSync("synchronized A");
                }

                System.out.println("------------------ Sync A : " + (System.currentTimeMillis() - startSync));
            }).start();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    jkLock.checkSync("synchronized B");
                }

                System.out.println("------------------ Sync B : " + (System.currentTimeMillis() - startSync));
            }).start();

            long startReentrant = System.currentTimeMillis();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    jkLock.checkReentrantLock("Reentrant A");
                }

                System.out.println("------------------ Reentrant A : " + (System.currentTimeMillis() - startReentrant));
            }).start();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    jkLock.checkReentrantLock("Reentrant B");
                }

                System.out.println("------------------ Reentrant B : " + (System.currentTimeMillis() - startReentrant));
            }).start();

            long startNoneLock = System.currentTimeMillis();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    jkLock.noneLock("NoneLock A");
                }

                System.out.println("------------------ None A : " + (System.currentTimeMillis() - startNoneLock));
            }).start();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    jkLock.noneLock("NoneLock B");
                }

                System.out.println("------------------ None B : " + (System.currentTimeMillis() - startNoneLock));
            }).start();

            wait(1000);
        } catch (Exception ex) {
            System.out.println("J Tag");
        }
    }
}
