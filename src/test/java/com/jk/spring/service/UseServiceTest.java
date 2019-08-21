package com.jk.spring.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UseServiceTest {

    private final UseService useService;

    @Autowired
    public UseServiceTest(UseService useService) {
        this.useService = useService;
    }

    @Test
    public void test() {

    }
}
