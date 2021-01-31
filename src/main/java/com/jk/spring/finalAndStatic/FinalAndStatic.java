package com.jk.spring.finalAndStatic;

import java.util.ArrayList;
import java.util.List;

public class FinalAndStatic {

    public static class StaticInteger {
        int nonStaticInteger = 0;
        static int staticInteger = 0;

        StaticInteger() {
            nonStaticInteger++;
            staticInteger++;
        }

        public void getStaticInteger() {
            System.out.println("static integer : " + staticInteger);
        }
    }

    public void finalTest () {
        try {
            final List<String> finalStrings = new ArrayList<>();

            System.out.println("list size : " + finalStrings.size());

            finalStrings.add("test1");
            finalStrings.add("test2");
            finalStrings.add("test3");

            System.out.println("list size : " + finalStrings.size());

            if (finalStrings.size() > 0) {
                for (String finalString : finalStrings) {
                    System.out.println("list element : " + finalString);
                }
            }

            finalStrings.add("test5");
            finalStrings.add("test6");
            finalStrings.add("test7");

            if (finalStrings.size() > 0) {
                for (String finalString : finalStrings) {
                    System.out.println("again, list element : " + finalString);
                }
            }

            System.out.println("list size : " + finalStrings.size());
        } catch (Exception ex) {
            System.out.println("exception occur : " + ex.getMessage());
        }
    }

    public void staticTest() {
        StaticInteger one = new StaticInteger();

        System.out.println("first : " + one.nonStaticInteger);
        one.getStaticInteger();

        StaticInteger two = new StaticInteger();

        System.out.println("second : " + two.nonStaticInteger);
        two.getStaticInteger();
    }
}
