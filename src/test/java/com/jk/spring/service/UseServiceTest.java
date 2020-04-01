package com.jk.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UseServiceTest {

    private final UseService useService;

    @Autowired
    public UseServiceTest(UseService useService) {
        this.useService = useService;
    }

    @Test
    public void test() {
        useService.useInterface();
        useService.useStartegy();
        useService.useOverLoadingAndRiding("J Tag?");
    }
}
