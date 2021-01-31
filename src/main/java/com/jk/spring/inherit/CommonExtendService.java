package com.jk.spring.inherit;

import org.springframework.stereotype.Service;

@Service
public class CommonExtendService extends CommonService {

    @Override
    public void read() {
        System.out.println("J Tag");
    }

    public void newRead() {
        System.out.println("new read");
    }

    public void newWrite() {
        System.out.println("new read");
    }
}
