package com.jk.spring;

import com.jk.spring.finalAndStatic.FinalAndStatic;
import com.jk.spring.finalAndStatic.JKStaticMethod;
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

        // 둘 다 사용 가능하다. 다만 static method 의 경우 별도의 instance 가 필요 없이 (new instance 를 의미) class 내에서 바로 호출 가능하다.
        // 그 이유는 이미 static 으로 memory 위에 올라가 있고 compile 이 끝난 상태이기 때문이다.
        JKStaticMethod jkStaticMethod = new JKStaticMethod();
        jkStaticMethod.staticMethod();
        JKStaticMethod.staticMethod();
    }
}
