package com.jk.spring.inherit;

import org.springframework.stereotype.Service;

/**
 * ICommonInterface 를 받아서 override 를 통해 내부 logic 을 변경하여 사용할 수 있고,
 * 변경하지 않을 경우 코드 재사용을 통해 기존 CommonService 에 호출된 내역을 토대로 호출된다.
 * 또한 ICommonAInterface 를 통해 override 로 코드를 수정하여 사용한다.
 */
@Service
public class CommonAService extends CommonService implements ICommonAInterface {

//    @Override
//    public void read() {
//        System.out.println("read CommonAService");
//    }
//
//    @Override
//    public void write() {
//        System.out.println("write CommonAService");
//    }

    @Override
    public void update() {
        System.out.println("update CommonAService");
    }

    @Override
    public void list() {
        System.out.println("list CommonAService");
    }
}
