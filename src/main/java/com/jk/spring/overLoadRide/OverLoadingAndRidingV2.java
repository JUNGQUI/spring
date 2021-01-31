package com.jk.spring.overLoadRide;

import org.springframework.stereotype.Service;

/**
 * v1과 다른 로직 (void 의 경우 J tag -> v2 이다 로 출력 / String 의 경우 suffix 에 v2 를 붙임) 이 되었지만,
 * return 은 그대로 유지, 재정의
 */
@Service
public class OverLoadingAndRidingV2 implements IOverLoadingAndRiding {

    @Override
    public void loadingTest() {
        System.out.println("This is v2 ridingTest.");
    }

    @Override
    public String loadingTest(String returnValue) {
        returnValue += " V2";
        System.out.println("This is v2 ridingTest, " + returnValue);
        return returnValue;
    }
}
