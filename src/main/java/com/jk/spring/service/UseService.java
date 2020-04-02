package com.jk.spring.service;

import com.jk.spring.inherit.ICommonAInterface;
import com.jk.spring.inherit.ICommonInterface;
import com.jk.spring.overLoadRide.IOverLoadingAndRiding;
import com.jk.spring.strategy.IContextMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UseService {

    private final ICommonInterface iCommonInterface;
    private final ICommonInterface iACommonInterface;
    private final ICommonAInterface iCommonAInterface;

    private final IContextMethod iContextMethodA;
    private final IContextMethod iContextMethodB;

    private final IOverLoadingAndRiding overLoadingAndRidingV1;
    private final IOverLoadingAndRiding overLoadingAndRidingV2;

    @Autowired
    public UseService(@Qualifier("commonService") ICommonInterface iCommonInterface,
                      @Qualifier("commonAService") ICommonInterface iACommonInterface,
                      ICommonAInterface iCommonAInterface,
                      @Qualifier("contextMethodA") IContextMethod iContextMethodA,
                      @Qualifier("contextMethodB") IContextMethod iContextMethodB,
                      @Qualifier("overLoadingAndRidingV1") IOverLoadingAndRiding overLoadingAndRidingV1,
                      @Qualifier("overLoadingAndRidingV2") IOverLoadingAndRiding overLoadingAndRidingV2) {
        this.iCommonInterface = iCommonInterface;
        this.iACommonInterface = iACommonInterface;
        this.iCommonAInterface = iCommonAInterface;
        this.iContextMethodA = iContextMethodA;
        this.iContextMethodB = iContextMethodB;
        this.overLoadingAndRidingV1 = overLoadingAndRidingV1;
        this.overLoadingAndRidingV2 = overLoadingAndRidingV2;
    }

    public void useInterface () {
        print("common interface.");
        // 공용의 method 에 별도의 override 된 method
        iCommonInterface.read();
        iCommonInterface.write();

        print("A common interface, not override.");
        // Qualifier 를 통해 어떤 override 된 method 를 쓸지 정할 수 있다.
        iACommonInterface.read();
        iACommonInterface.write();

        print("A common interface, implement.");
        // 새로 생겨난 list, update interface
        iCommonAInterface.list();
        iCommonAInterface.update();
    }

    public void useStartegy () {
        print("use A strategy.");
        iContextMethodA.contextMethod("choose strategy ", "A");
        print("use B strategy.");
        iContextMethodB.contextMethod("choose strategy ", "B");
    }

    public void useOverLoadingAndRiding(String returnValue) {
        print("over v1.");
        overLoadingAndRidingV1.loadingTest();
        overLoadingAndRidingV1.loadingTest(returnValue);

        print("over v2.");
        overLoadingAndRidingV2.loadingTest();
        overLoadingAndRidingV2.loadingTest(returnValue);
    }

    private void print(String value) {
        System.out.println(value);
    }
}
