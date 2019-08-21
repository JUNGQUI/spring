package com.jk.spring.inherit;

import org.springframework.stereotype.Service;

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
