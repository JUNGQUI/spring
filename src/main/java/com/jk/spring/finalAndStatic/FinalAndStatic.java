package com.jk.spring.finalAndStatic;

import java.util.ArrayList;
import java.util.List;

public class FinalAndStatic {

    static List<String> STATIC_STRING = new ArrayList<>();

    public void finalAndStatic () {
        try {
            final List<String> finalString = new ArrayList<>();

            finalString.add("test1");
            finalString.add("test2");
            finalString.add("test3");

            STATIC_STRING.addAll(finalString);

            finalString.add("test5");
            finalString.add("test6");
            finalString.add("test7");

            STATIC_STRING.addAll(finalString);

//            finalString = new ArrayList<>();
            // error 가 발생한다, IDE 가 좋아서 그런지 아예 디버깅이 안된다.
            // 다만 중점적으로 봐야 할 부분은 final 이 완벽한 immutable 이 아니라는 것, 할당 이후 list 나 array 일 경우 값을 추가함으로
            // 값의 변화가 일어 날 수 있다. 물론, String 이나 (string 은 애시당초 immutable 이지만) int 와 같은 값으로 재할당은 당연히 안된다.
        } catch (Exception ex) {
            this.staticTest();
        }
    }

    private void staticTest() {
        for (String temp : STATIC_STRING) {
            System.out.println(temp);
        }
    }
}
