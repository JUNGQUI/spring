package com.jk.spring.IOCAndDI;

import org.springframework.stereotype.Service;

@Service
public class TransportCar implements ITransport {

    @Override
    public void transfer() {
        System.out.println("IOC, DI : 차로 이동 중");
    }
}
