package com.jk.spring.inherit;

import org.springframework.stereotype.Service;

/**
 * ICommonInterface 를 받아서 override 를 통해 내부 logic 을 변경하여 사용
 */

@Service
public class CommonService implements ICommonInterface {
    @Override
    public void read() {
        System.out.println("read CommonService");
    }

    @Override
    public void write() {
        System.out.println("write CommonService");
    }

}
