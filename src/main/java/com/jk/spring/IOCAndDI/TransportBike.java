package com.jk.spring.IOCAndDI;

import org.springframework.stereotype.Service;

@Service
public class TransportBike implements ITransport {

    @Override
    public void transfer() {
        System.out.println("IOC, DI : 오토바이로 이동 중");
    }
}
