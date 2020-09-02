package com.jk.spring;

import com.jk.spring.finalAndStatic.FinalAndStatic;
import org.junit.Test;

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
    }
}
