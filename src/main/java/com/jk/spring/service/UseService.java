package com.jk.spring.service;

import com.jk.spring.inherit.ICommonAInterface;
import com.jk.spring.inherit.ICommonInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UseService {

    private final ICommonInterface iCommonInterface;
    private final ICommonInterface iACommonInterface;
    private final ICommonAInterface iCommonAInterface;

    @Autowired
    public UseService(@Qualifier("commonService") ICommonInterface iCommonInterface,
                      @Qualifier("commonAService") ICommonInterface iACommonInterface,
                      ICommonAInterface iCommonAInterface) {
        this.iCommonInterface = iCommonInterface;
        this.iACommonInterface = iACommonInterface;
        this.iCommonAInterface = iCommonAInterface;
    }

    public void useInterface () {
        // 공용의 method 에 별도의 override 된 method
        iCommonInterface.read();
        iCommonInterface.write();
        // Qualifier 를 통해 어떤 override 된 method 를 쓸지 정할 수 있다.
        iACommonInterface.read();
        iACommonInterface.write();

        // 새로 생겨난 list, update interface
        iCommonAInterface.list();
        iCommonAInterface.update();
    }
}
