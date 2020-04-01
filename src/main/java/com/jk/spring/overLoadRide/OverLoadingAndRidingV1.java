package com.jk.spring.overLoadRide;

import org.springframework.stereotype.Service;

@Service
public class OverLoadingAndRidingV1 implements IOverLoadingAndRiding {

    /**
     * 오버로딩을 통해 loadingTest 라는 method 를 다수 입맛에 맞게 생성하고,
     * Interface 를 만들어 외부 관심사를 단절하여 override 를 통해 class 내에서 logic 을 변경하였다.
     */
    @Override
    public void loadingTest() {
        System.out.println("J Tag");
    }

    @Override
    public String loadingTest(String returnValue) {
        System.out.println("J Tag " + returnValue);
        return returnValue;
    }
}
