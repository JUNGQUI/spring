package com.jk.spring.inherit;

import org.springframework.stereotype.Service;

@Service
public class CommonAService extends CommonService implements ICommonAInterface {

    @Override
    public void read() {
        System.out.println("read CommonAService");
    }

    @Override
    public void write() {
        System.out.println("write CommonAService");
    }

    @Override
    public void update() {
        System.out.println("update CommonAService");
    }

    @Override
    public void list() {
        System.out.println("list CommonAService");
    }
}
